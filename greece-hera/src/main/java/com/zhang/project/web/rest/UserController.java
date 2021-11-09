package com.zhang.project.web.rest;

import com.zhang.common.core.restful.CommonRestResult;
import com.zhang.common.core.template.RestBusinessTemplate;
import com.zhang.project.biz.manager.UserManager;
import com.zhang.project.web.form.UserForm;
import com.zhang.project.web.form.UserInfoForm;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户表(User)表控制层
 *
 * @author Distance
 * @since 2021-10-09 14:16:03
 */
@RestController
@RequestMapping("/**/user")
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private UserManager userManager;

    @ApiOperation(value = "登录",notes = "登录")
    @PostMapping("/login")
    public CommonRestResult login(@RequestBody UserForm userForm){
        return RestBusinessTemplate.execute(()->userManager.login(userForm));
    }

    @ApiOperation(value = "用户列表",notes = "用户列表")
    @PostMapping("/list")
    public CommonRestResult list(@RequestBody UserForm userForm){
        return RestBusinessTemplate.execute(()->userManager.findUserList(userForm));
    }

    @ApiOperation(value = "新增用户",notes = "新增用户")
    @PostMapping("/create")
    public CommonRestResult create(@RequestBody UserForm userForm){
        return RestBusinessTemplate.transaction(()->{
            userManager.createUser(userForm);
            return null;
        });
    }

    @ApiOperation(value = "修改用户状态",notes = "修改用户状态")
    @PostMapping("/updateStatus")
    public CommonRestResult updateStatus(@RequestBody UserForm userForm){
        return RestBusinessTemplate.transaction(()->{
            userManager.updateStatus(userForm);
            return null;
        });
    }

    @ApiOperation(value = "新增用户信息",notes = "新增用户信息")
    @PostMapping("/createUserInfo")
    public CommonRestResult createUserInfo(@RequestBody UserInfoForm form){
        return RestBusinessTemplate.transaction(()->{
            userManager.createUserInfo(form);
            return null;
        });
    }


    @ApiOperation(value = "获取用户信息列表",notes = "新增用户信息")
    @GetMapping("/userInfo/list")
    public CommonRestResult getUserInfoList(){
        return RestBusinessTemplate.execute(()-> userManager.getUserInfoList());
    }

}
