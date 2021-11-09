package com.zhang.project.biz.manager;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.common.core.exception.BizCoreException;
import com.zhang.project.biz.util.RedisUtils;
import com.zhang.project.dal.dao.InfoDAO;
import com.zhang.project.dal.dataobject.Info;
import com.zhang.project.web.form.InfoForm;
import com.zhang.project.web.vo.InfoVO;
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
 * (Info)表服务实现类
 *
 * @author Distance
 * @since 2021-10-11 18:51:08
 */
@Service("infoManager")
public class InfoManager {

    @Resource
    private InfoDAO infoDAO;

    @Resource
    private RedisUtils redisUtils;

    public Page<InfoVO> findInfoList(InfoForm form){

        Page<Info> page = new Page<>(form.getCurrent(),form.getSize());
        QueryWrapper<Info> wrapper = new QueryWrapper<>();
        List<Info> records = infoDAO.selectPage(page,wrapper).getRecords();
        List<InfoVO> voList = records.stream().map(info -> coverInfoVO(info)).collect(Collectors.toList());
        Page<InfoVO> result = new Page<>(form.getCurrent(),form.getSize());
        result.setRecords(voList);
        result.setTotal(voList.size());
        return result;
    }

    private InfoVO coverInfoVO(Info info){
        return InfoVO.builder().id(info.getId()).openId(info.getOpenId())
                .title(info.getTitle()).style(info.getStyle())
                .author(info.getAuthor()).createTime(info.getCreateTime())
                .views(info.getViews()).content(info.getContent()).mdContent(info.getMdContent())
                .digest(info.getDigest()).build();
    }

    /**
     * 根据id删除公告
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteInfoById(Integer id){

        int delete = infoDAO.deleteById(id);
        if(delete == 0){
            throw new BizCoreException("删除公告失败");
        }
    }

    /**
     * 新增公告
     * @param form
     */
    public void createInfo(InfoForm form){

    }

    /**
     * 修改公告
     * @param form
     */
    public void updateInfo(InfoForm form){

    }

    /**
     * 获取公告详情
     * @param openId
     * @return
     */
    public InfoVO findInfoByOpenId(String openId){
        Info info = infoDAO.selectOne(new QueryWrapper<Info>().eq("open_id",openId));
        if(ObjectUtil.isNull(info)){
            throw new BizCoreException("没有找到该公告");
        }
        Integer views = info.getViews();
        String key = "info_"+openId+"_views";
        if(redisUtils.hasKey(key)){
            Integer redisViews  = (Integer) redisUtils.get(key);
            if(redisViews <= views + 5){
                redisUtils.incrBy(key,1);
            }else{
                int update = infoDAO.updateViewById(info.getId(), redisViews);
                if(update == 0){
                    throw new BizCoreException("修改公告访问量失败");
                }
                info.setViews(redisViews);
                redisUtils.del(key);
            }
        }else {
            redisUtils.set(key,views);
        }
        return coverInfoVO(info);
    }
}
