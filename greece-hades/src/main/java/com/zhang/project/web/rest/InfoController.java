package com.zhang.project.web.rest;

import com.zhang.common.core.restful.CommonRestResult;
import com.zhang.common.core.template.RestBusinessTemplate;
import com.zhang.project.biz.manager.InfoManager;
import com.zhang.project.web.form.InfoForm;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Info)表控制层
 *
 * @author Distance
 * @since 2021-10-11 18:51:07
 */
@RestController
@RequestMapping("info")
public class InfoController {
    /**
     * 服务对象
     */
    @Resource
    private InfoManager infoManager;

    @ApiOperation(value = "分页查找所有公告列表",notes = "分页查找所有公告列表")
    @GetMapping("/list")
    public CommonRestResult findInfoList(InfoForm form){
        return RestBusinessTemplate.execute(()->infoManager.findInfoList(form));
    }

    @ApiOperation(value = "根据id删除公告",notes = "根据id删除公告")
    @PostMapping("/delete")
    public CommonRestResult deleteInfoById(Integer id){
        return RestBusinessTemplate.transaction(()->{
            infoManager.deleteInfoById(id);
            return null;
        });
    }

    @ApiOperation(value = "添加公告",notes = "添加公告")
    @PostMapping("/create")
    public CommonRestResult createInfo(@RequestBody InfoForm form){
        return RestBusinessTemplate.transaction(()->{
            infoManager.createInfo(form);
            return null;
        });
    }

    @ApiOperation(value = "修改公告",notes = "修改公告")
    @PostMapping("/update")
    public CommonRestResult updateInfo(@RequestBody InfoForm form){
        return RestBusinessTemplate.transaction(()->{
            infoManager.updateInfo(form);
            return null;
        });
    }
    
    @ApiOperation(value = "公告详情",notes = "公告详情")
    @GetMapping("/detail")
    public CommonRestResult findInfoByOpenId(@RequestParam("openId") String openId){
        return RestBusinessTemplate.execute(()->infoManager.findInfoByOpenId(openId));
    }

   
}
