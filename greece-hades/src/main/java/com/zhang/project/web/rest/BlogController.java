package com.zhang.project.web.rest;

import com.zhang.common.core.restful.CommonRestResult;
import com.zhang.common.core.template.RestBusinessTemplate;
import com.zhang.integration.user.client.UserClient;
import com.zhang.project.biz.manager.BlogManager;
import com.zhang.project.web.form.BasePageForm;
import com.zhang.project.web.form.BlogForm;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Blog)表控制层
 *
 * @author Distance
 * @since 2021-10-11 10:09:00
 */
@RestController
@RequestMapping("blog")
public class BlogController {
    /**
     * 服务对象
     */
    @Resource
    private BlogManager blogManager;

    @Resource
    private UserClient userClient;

    @ApiOperation(value = "分页查找所有博客列表",notes = "分页查找所有博客列表")
    @GetMapping("/list")
    public CommonRestResult findBlogList(BlogForm form){
        return RestBusinessTemplate.execute(()->blogManager.findBlogList(form));
    }

    @ApiOperation(value = "根据id删除博客",notes = "根据id删除博客")
    @PostMapping("/delete")
    public CommonRestResult deleteBlogById(@RequestBody BlogForm form){
        return RestBusinessTemplate.transaction(()->{
            blogManager.deleteBlogById(form.getId());
            return null;
        });
    }

    @ApiOperation(value = "添加博客",notes = "添加博客")
    @PostMapping("/create")
    public CommonRestResult createBlog(@RequestBody BlogForm form){
        return RestBusinessTemplate.transaction(()->{
            blogManager.createBlog(form);
            return null;
        });
    }

    @ApiOperation(value = "修改博客",notes = "修改博客")
    @PostMapping("/update")
    public CommonRestResult updateBlog(@RequestBody BlogForm form){
        return RestBusinessTemplate.transaction(()->{
            blogManager.updateBlog(form);
            return null;
        });
    }

    @ApiOperation(value = "热门博客推荐",notes = "热门博客推荐")
    @GetMapping("/list/recommend")
    public CommonRestResult findRecommendBlog(){
        return RestBusinessTemplate.execute(()->blogManager.findFiveBlogByViewsDesc());
    }

    @ApiOperation(value = "博客详情",notes = "博客详情")
    @GetMapping("/detail")
    public CommonRestResult findBlogByOpenId(@RequestParam("openId") String openId){
        return RestBusinessTemplate.execute(()->blogManager.findBlogByOpenId(openId));
    }

    @ApiOperation(value = "获取所有type列表",notes = "获取所有type列表")
    @GetMapping("/list/type")
    public CommonRestResult findTypeList(){
        return RestBusinessTemplate.execute(()->blogManager.findTypeList());
    }

    @ApiOperation(value = "获取所有label列表",notes = "获取所有label列表")
    @GetMapping("/list/label")
    public CommonRestResult findLabelList(){
        return RestBusinessTemplate.execute(()->blogManager.findLabelList());
    }

    @ApiOperation(value = "增加博客访问量" ,notes = "增加博客访问量")
    @GetMapping("/addViews")
    public CommonRestResult addViews(@RequestParam("id")Integer id){
        return RestBusinessTemplate.transaction(()->{
            blogManager.addViews(id);
            return null;
        });
    }

    @ApiOperation(value = "获取用户信息列表",notes = "新增用户信息")
    @GetMapping("/userInfo/list")
    public CommonRestResult getUserInfoList(){
        return RestBusinessTemplate.execute(()-> userClient.getUserInfoList().getContent());
    }

}
