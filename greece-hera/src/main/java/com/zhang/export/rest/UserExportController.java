package com.zhang.export.rest;

import com.zhang.common.core.restful.CommonRestResult;
import com.zhang.common.core.template.RestBusinessTemplate;
import com.zhang.project.biz.manager.UserManager;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Yaohang Zhang
 * @ClassName UserController
 * @description TODO
 * @date 2021-10-10 22:57
 */
@RestController
@RequestMapping("/hera/user")
public class UserExportController {

    @Autowired
    private UserManager userManager;

    @ApiOperation(value = "根据openId获取用户姓名",notes = "根据openId获取用户姓名")
    @GetMapping("/findNameByOpenId")
    public String findNameByOpenId(@RequestParam("openId")String openId){
        return userManager.findNameByUserOpenId(openId);
    }

    @ApiOperation(value = "根据openId获取用户头像",notes = "根据openId获取用户头像")
    @GetMapping("/findAvatarUserOpenId")
    public String findAvatarUserOpenId(@RequestParam("openId")String openId){
        return userManager.findAvatarUserOpenId(openId);
    }

    @ApiOperation(value = "根据openId修改用户头像",notes = "根据openId修改用户头像")
    @PostMapping("/updateAvatarUserOpenId")
    public void updateAvatarUserOpenId(@RequestParam("openId")String openId,@RequestParam("avatar")String avatar){
        userManager.updateAvatarUserOpenId(openId,avatar);
    }

    @ApiOperation(value = "获取用户信息列表",notes = "新增用户信息")
    @GetMapping("/userInfo/list")
    public CommonRestResult getUserInfoList(){
        return RestBusinessTemplate.execute(()->userManager.getUserInfoList());
    }
}
