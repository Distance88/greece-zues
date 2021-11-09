package com.zhang.project.web.rest;

import com.zhang.common.core.restful.CommonRestResult;
import com.zhang.common.core.template.RestBusinessTemplate;
import com.zhang.project.biz.manager.MenuManager;
import com.zhang.project.biz.manager.RoleManager;
import com.zhang.project.web.form.RoleForm;
import com.zhang.project.web.form.RoleMenuForm;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 角色信息表(Role)表控制层
 *
 * @author Distance
 * @since 2021-10-09 14:55:58
 */
@RestController
@RequestMapping("/**/user/role")
public class RoleController {
    /**
     * 服务对象
     */
    @Resource
    private RoleManager roleManager;

    @Resource
    private MenuManager menuManager;

    @ApiOperation(value = "用户列表",notes = "用户列表")
    @PostMapping("/list")
    public CommonRestResult list(@RequestBody RoleForm roleForm){
        return RestBusinessTemplate.execute(()->roleManager.findRoleList(roleForm));
    }

    @ApiOperation(value = "修改用户状态",notes = "修改用户状态")
    @GetMapping("/updateStatus")
    public CommonRestResult updateStatus(Integer id){
        return RestBusinessTemplate.transaction(()->{
            roleManager.updateStatus(id);
            return null;
        });
    }

    @ApiOperation(value = "获取角色菜单列表",notes = "获取角色菜单列表")
    @GetMapping("/menuList")
    public CommonRestResult menuList(String roleType){
        return RestBusinessTemplate.execute(()->menuManager.getMenuList(roleType));
    }

    @ApiOperation(value = "获取角色菜单列表",notes = "获取角色菜单列表")
    @PostMapping("/updateMenuList")
    public CommonRestResult updateMenuList(@RequestBody RoleMenuForm form){
        return RestBusinessTemplate.execute(()->{
            roleManager.updateMenuList(form);
            return null;
        });
    }

}
