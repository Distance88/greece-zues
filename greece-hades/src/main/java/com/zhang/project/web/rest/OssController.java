package com.zhang.project.web.rest;

import com.zhang.common.core.restful.CommonRestResult;
import com.zhang.common.core.template.RestBusinessTemplate;
import com.zhang.project.biz.manager.OssManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author Yaohang Zhang
 * @ClassName OssController
 * @description TODO
 * @date 2021-10-15 10:19
 */
@Api("oss相关模块")
@RestController
@RequestMapping("oss")
public class OssController {

    @Resource
    private OssManager ossManager;


    @ApiOperation(value = "上传图片",notes = "上传图片")
    @PostMapping("/uploadImage")
    public CommonRestResult uploadImage(MultipartFile multipartFile){
        return RestBusinessTemplate.execute(()->ossManager.uploadImage(multipartFile));
    }

    @ApiOperation(value = "删除图片",notes = "删除图片")
    @GetMapping("/deleteImage")
    public CommonRestResult deleteImage(@RequestParam("fileName") String fileName){
        return RestBusinessTemplate.execute(()->{
            ossManager.deleteImage(fileName);
            return null;
        });
    }

    @ApiOperation(value = "上传头像",notes = "上传头像")
    @PostMapping("/uploadAvatar")
    public CommonRestResult uploadAvatar(@RequestParam("multipartFile") MultipartFile multipartFile,@RequestParam("userOpenId")String userOpenId){
        return RestBusinessTemplate.transaction(()->ossManager.uploadAvatar(multipartFile, userOpenId));
    }


}
