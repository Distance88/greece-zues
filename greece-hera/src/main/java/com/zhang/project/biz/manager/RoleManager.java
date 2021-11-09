package com.zhang.project.biz.manager;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.common.core.exception.BizCoreException;
import com.zhang.project.dal.dao.MenuDAO;
import com.zhang.project.dal.dao.RoleDAO;
import com.zhang.project.dal.dataobject.Menu;
import com.zhang.project.dal.dataobject.Role;
import com.zhang.project.web.form.RoleForm;
import com.zhang.project.web.form.RoleMenuForm;
import com.zhang.project.web.vo.SideBarVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author Distance
 * @since 2021-10-09 14:55:59
 */
@Service("roleManager")
public class RoleManager {

    @Resource
    private RoleDAO roleDAO;

    @Resource
    private MenuDAO menuDAO;

    public Page<Role> findRoleList(RoleForm form){
        Page<Role> page = new Page<>(form.getCurrent(),form.getSize());
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        Map<String,Object> map = new HashMap<>();
        if(StringUtils.isNotBlank(form.getType())){
            map.put("type",form.getType());
        }
        if(StringUtils.isNotBlank(form.getName())){
            map.put("name",form.getName());
        }
        wrapper.allEq(map);
        return (Page<Role>) roleDAO.selectPage(page,wrapper);
    }

    public void updateStatus(Integer id){
        roleDAO.updateStatusById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateMenuList(RoleMenuForm form){

        List<Integer> newIdList = form.getMenuIdList();
        String roleType = form.getRoleType();
        List<Integer> oldIdList = menuDAO.selectList(null)
                .stream().filter(menu-> JSONArray.parseArray(menu.getRole(),String.class).contains(roleType))
                .map(menu -> {
                    return menu.getId();
                })
                .collect(Collectors.toList());
        Set<Integer> allIdList = new HashSet<>();
        allIdList.addAll(newIdList);
        allIdList.addAll(oldIdList);

        for(Integer id:allIdList){
            Boolean canUpdate = false;
            UpdateWrapper<Menu> wrapper = new UpdateWrapper<>();
            Menu menu = menuDAO.selectById(id);
            if(ObjectUtil.isNull(menu)){
                throw new BizCoreException("没有找到该菜单");
            }
            String role = menu.getRole();
            List<String> roleList = new ArrayList<>();
            if(newIdList.contains(id) && !oldIdList.contains(id)){
                roleList = JSONArray.parseArray(role, String.class);
                roleList.add(roleType);
                canUpdate = true;
            }
            if(!newIdList.contains(id) && oldIdList.contains(id)){
                roleList = JSONArray.parseArray(role, String.class);
                roleList.remove(roleType);
                canUpdate = true;
            }
            if(canUpdate){
                int update = menuDAO.updateRoleById(menu.getId(), JSON.toJSONString(roleList));
                if(update == 0){
                    throw new BizCoreException("修改失败");
                }
            }
        }
    }
}
