package com.zhang.project.biz.manager;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.common.core.exception.BizCoreException;
import com.zhang.integration.user.client.UserClient;
import com.zhang.project.biz.util.MarkdownUtils;
import com.zhang.project.biz.util.RedisUtils;
import com.zhang.project.dal.dao.BlogCommentDAO;
import com.zhang.project.dal.dao.BlogDAO;
import com.zhang.project.dal.dao.BlogLabelDAO;
import com.zhang.project.dal.dao.BlogTypeDAO;
import com.zhang.project.dal.dataobject.Blog;
import com.zhang.project.dal.dataobject.BlogComment;
import com.zhang.project.dal.dataobject.BlogLabel;
import com.zhang.project.dal.dataobject.BlogType;
import com.zhang.project.web.form.BlogForm;
import com.zhang.project.web.vo.BlogCommentVO;
import com.zhang.project.web.vo.BlogVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * (Blog)表服务实现类
 *
 * @author Distance
 * @since 2021-10-11 10:09:02
 */
@Service("blogManager")
public class BlogManager {

    @Resource
    private BlogDAO blogDAO;

    @Resource
    private UserClient userClient;

    @Resource
    private BlogCommentDAO blogCommentDAO;

    @Resource
    private BlogLabelDAO blogLabelDAO;

    @Resource
    private BlogTypeDAO blogTypeDAO;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private UserBlogLogRecordManager userBlogLogRecordManager;

    public Page<BlogVO> findBlogList(BlogForm form) {

        Page<Blog> page = new Page<>(form.getCurrent(), form.getSize());
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(form.getType())) {
            map.put("type", form.getType());
        }
        if (StringUtils.isNotBlank(form.getStyle())) {
            map.put("style", form.getStyle());
        }
        if (StringUtils.isNotBlank(form.getUserOpenId())) {
            if (StringUtils.isBlank(form.getRoleType()) || StringUtils.equals(form.getRoleType(), "USER")) {
                map.put("user_open_id", form.getUserOpenId());
            }
        }
        if (StringUtils.isNotBlank(form.getAuthor())) {
            map.put("author", form.getAuthor());
        }
        if (StringUtils.isNotBlank(form.getLabel())) {
            wrapper.like("label", form.getLabel());
        } else {
            wrapper.allEq(map);
        }
        List<Blog> records = blogDAO.selectPage(page, wrapper).getRecords();
        List<BlogVO> voList = records.stream().map(blog -> coverBlogVO(blog)).collect(Collectors.toList());
        Page<BlogVO> result = new Page<>(form.getCurrent(), form.getSize());
        result.setRecords(voList);
        result.setTotal(page.getTotal());
        return result;
    }

    private BlogVO coverBlogVO(Blog blog) {
        String avatar = userClient.findAvatarUserOpenId(blog.getUserOpenId());
        List<BlogCommentVO> blogCommentVO = getBlogCommentVO(blog.getOpenId());
        return BlogVO.builder().id(blog.getId()).openId(blog.getOpenId())
                .userOpenId(blog.getUserOpenId()).type(blog.getType()).title(blog.getTitle())
                .label(blog.getLabel()).style(blog.getStyle()).author(blog.getAuthor()).createTime(blog.getCreateTime())
                .views(blog.getViews()).content(blog.getContent()).mdContent(blog.getMdContent())
                .charts(blog.getCharts()).charts(blog.getCharts()).blogCommentVOList(blogCommentVO)
                .avatar(avatar).digest(blog.getDigest()).build();
    }

    private List<BlogCommentVO> getBlogCommentVO(String blogOpenId) {

        QueryWrapper<BlogComment> wrapper = new QueryWrapper<>();
        wrapper.eq("blog_open_id", blogOpenId);
        List<BlogComment> blogCommentList = blogCommentDAO.selectList(wrapper);
        List<BlogCommentVO> blogCommentVOS = new ArrayList<>();
        for (BlogComment blogComment : blogCommentList) {
            if (StringUtils.equals("0", blogComment.getReplyOpenId())) {
                blogCommentVOS.add(coverBlogCommentVO(blogComment));
            }
        }
        for (BlogCommentVO blogCommentVO : blogCommentVOS) {
            List<BlogCommentVO> children = new ArrayList<>();
            for (BlogComment blogComment : blogCommentList) {
                if (StringUtils.equals(blogComment.getReplyOpenId(), blogCommentVO.getReplyOpenId())) {
                    children.add(coverBlogCommentVO(blogComment));
                }
            }
            blogCommentVO.setChildren(children);
        }
        return blogCommentVOS;
    }

    private BlogCommentVO coverBlogCommentVO(BlogComment blogComment) {
        return BlogCommentVO.builder()
                .openId(blogComment.getOpenId()).name(blogComment.getName())
                .content(blogComment.getContent()).replyName(blogComment.getReplyName())
                .replyOpenId(blogComment.getReplyOpenId()).blogOpenId(blogComment.getBlogOpenId()).build();
    }

    /**
     * 根据id删除博客
     *
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteBlogById(Integer id) {
        Blog blog = blogDAO.selectById(id);
        if (ObjectUtil.isNull(blog)) {
            throw new BizCoreException("要删除的博客不存在!");
        }
        //修改或添加博客标签
        List<String> labelList = JSONArray.parseArray(blog.getLabel(), String.class);
        for (String label : labelList) {
            updateBlogLabelCount(label,"decrease");
        }
        //修改或添加博客类型
        updateBlogTypeCount(blog.getType(), "decrease");
        int delete = blogDAO.deleteById(id);
        if (delete == 0) {
            throw new BizCoreException("删除博客失败");
        }
        userBlogLogRecordManager.createUserBlogLogRecord(blog,"删除");
    }

    /**
     * 新增博客
     *
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public void createBlog(BlogForm form) {

        Blog blog = new Blog();
        String openId = IdUtil.fastSimpleUUID();
        blog.setOpenId(openId);
        blog.setUserOpenId(form.getUserOpenId());
        blog.setTitle(form.getTitle());
        blog.setDigest(form.getDigest());
        blog.setCharts(form.getCharts());
        blog.setContent(MarkdownUtils.transferMarkDownToHtml(form.getContent()));
        blog.setMdContent(form.getMdContent());
        blog.setLabel(form.getLabel());
        blog.setType(form.getType());
        blog.setStyle(form.getStyle());
        blog.setAuthor(form.getAuthor());
        blog.setViews(0);

        //修改或添加博客标签
        List<String> labelList = JSONArray.parseArray(form.getLabel(), String.class);
        for (String label : labelList) {
            updateBlogLabelCount(label,"increase");
        }
        //修改或添加博客类型
        updateBlogTypeCount(form.getType(), "increase");
        //添加博客新增记录
        userBlogLogRecordManager.createUserBlogLogRecord(blog,"添加");
        blogDAO.insert(blog);
    }

    /**
     * 修改博客
     *
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateBlog(BlogForm form) {

        Blog blog = blogDAO.selectById(form.getId());
        if (ObjectUtil.isNull(blog)) {
            throw new BizCoreException("要修改的博客不存在!");
        }
        blog.setId(form.getId());
        blog.setTitle(form.getTitle());
        blog.setDigest(form.getDigest());
        blog.setCharts(form.getCharts());
        blog.setContent(MarkdownUtils.transferMarkDownToHtml(form.getContent()));
        blog.setMdContent(form.getMdContent());
        blog.setStyle(form.getStyle());

        List<String> oldLabel = JSONArray.parseArray(blog.getLabel(), String.class);
        List<String> newLabel = JSONArray.parseArray(form.getLabel(), String.class);
        List<String> removeLabelList = JSONArray.parseArray(blog.getLabel(), String.class);
        List<String> addLabelList = JSONArray.parseArray(form.getLabel(), String.class);
        removeLabelList.removeAll(newLabel);
        addLabelList.removeAll(oldLabel);
        for(String label : removeLabelList){
            updateBlogLabelCount(label,"decrease");
        }
        for(String label : addLabelList){
            updateBlogLabelCount(label,"increase");
        }
        userBlogLogRecordManager.createUserBlogLogRecord(blog,"修改");
        blog.setLabel(form.getLabel());
        blog.setType(form.getType());
        blogDAO.updateById(blog);
    }

    public void addViews(Integer id){
        Blog blog = blogDAO.selectById(id);
        if(blog == null){
            throw new BizCoreException("没有找到该博客");
        }
        int updateViewById = blogDAO.updateViewById(id, blog.getViews() + 1);
        if(updateViewById == 0){
            throw new BizCoreException("修改博客访问量失败");
        }
    }

    /**
     * 获取热门博客列表
     *
     * @return
     */
    public List<BlogVO> findFiveBlogByViewsDesc() {
        List<Blog> blogList = blogDAO.findFiveBlogByViewsDesc();
        List<BlogVO> collect = blogList.stream().map(blog -> coverBlogVO(blog)).collect(Collectors.toList());
        return collect;
    }

    /**
     * 获取博客详情
     *
     * @param openId
     * @return
     */
    public BlogVO findBlogByOpenId(String openId) {
        Blog blog = blogDAO.selectOne(new QueryWrapper<Blog>().eq("open_id", openId));
        if (ObjectUtil.isNull(blog)) {
            throw new BizCoreException("没有找到该博客");
        }
        Integer views = blog.getViews();
        String key = "blog_" + openId + "_views";
        if (redisUtils.hasKey(key)) {
            Integer redisViews = (Integer) redisUtils.get(key);
            if (redisViews <= views + 5) {
                redisUtils.incrBy(key, 1);
            } else {
                int update = blogDAO.updateViewById(blog.getId(), redisViews);
                if (update == 0) {
                    throw new BizCoreException("修改博客访问量失败");
                }
                blog.setViews(redisViews);
                redisUtils.del(key);
            }
        } else {
            redisUtils.set(key, views);
        }
        return coverBlogVO(blog);
    }

    private void updateBlogTypeCount(String type, String operation) {
        BlogType blogType = blogTypeDAO.findBlogTypeByName(type);
        if (ObjectUtil.isNull(blogType)) {
            blogType = new BlogType();
            blogType.setName(type);
            blogType.setCount(1);
            blogTypeDAO.insert(blogType);
        } else {
            if (StringUtils.equals("increase", operation)) {
                int update = blogTypeDAO.increaseCountById(blogType.getId());
                if (update == 0) {
                    throw new BizCoreException("修改博客分类次数失败");
                }
            }
            if (StringUtils.equals("decrease", operation)) {
                int update = blogTypeDAO.decreaseCountById(blogType.getId());
                if (update == 0) {
                    throw new BizCoreException("修改博客分类次数失败");
                }
            }

        }
    }

    private void updateBlogLabelCount(String label, String operation) {
        BlogLabel blogLabel = blogLabelDAO.findBlogLabelByName(label);
        if (ObjectUtil.isNull(blogLabel)) {
            blogLabel = new BlogLabel();
            blogLabel.setName(label);
            blogLabel.setCount(1);
            blogLabelDAO.insert(blogLabel);
        } else {
            if (StringUtils.equals("increase", operation)) {
                int update = blogLabelDAO.increaseCountById(blogLabel.getId());
                if (update == 0) {
                    throw new BizCoreException("修改博客标签次数失败");
                }
            }
            if (StringUtils.equals("decrease", operation)) {
                int update = blogLabelDAO.decreaseCountById(blogLabel.getId());
                if (update == 0) {
                    throw new BizCoreException("修改博客标签次数失败");
                }
            }

        }
    }

    /**
     * 获取所有博客类型列表
     *
     * @return
     */
    public List<BlogType> findTypeList() {
        return blogTypeDAO.selectList(null);
    }

    /**
     * 获取所有博客标签列表
     *
     * @return
     */
    public List<BlogLabel> findLabelList() {
        return blogLabelDAO.selectList(null);
    }

}
