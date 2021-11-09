package com.zhang.project.biz.manager;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhang.project.dal.dao.MenuDAO;
import com.zhang.project.dal.dataobject.Menu;
import com.zhang.project.web.vo.MenuVO;
import com.zhang.project.web.vo.MenuVO;
import com.zhang.project.web.vo.SideBarVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author Distance
 * @since 2021-10-09 14:56:21
 */
@Service("menuManager")
public class MenuManager {

    @Resource
    private MenuDAO menuDAO;


    public JSONObject getMenuList(String roleType) {

        List<Menu> menuList = menuDAO.selectList(null);
        List<MenuVO> menuVOList = menuList.stream().map(menu -> coverMenuVO(menu)).collect(Collectors.toList());
        if (menuVOList.isEmpty()) {
            return null;

        }
        List<MenuVO> voList = new ArrayList<>();
        for (MenuVO menuVO : menuVOList) {
            if (ObjectUtil.equal(0, menuVO.getParentId())) {
                voList.add(menuVO);
            }
        }
        for (MenuVO menuVO : voList) {
            List<MenuVO> childrenList = getChildrenList(menuVO.getId(), menuVOList);
            menuVO.setChildren(childrenList);
        }

        List<MenuVO> collect = voList.stream().filter(vo -> JSONArray.parseArray(vo.getRoleType(), String.class).contains(roleType))
                .collect(Collectors.toList());
        Set<Integer> checkedKeys = new HashSet<>();
        for(MenuVO menuVO:collect){
            checkedKeys.add(menuVO.getId());
            if(!menuVO.getChildren().isEmpty()){
                for(MenuVO menuVO1:menuVO.getChildren()){
                    if(JSONArray.parseArray(menuVO1.getRoleType(),String.class).contains(roleType)){
                        checkedKeys.add(menuVO1.getId());
                    }
                }
            }
        }
        JSONObject object = new JSONObject();
        object.put("menuList",voList);
        object.put("checkedKeys",checkedKeys);
        return object;
    }

    /**
     * 递归添加子菜单
     * @param id
     * @param menuList
     * @return
     */
    private List<MenuVO> getChildrenList(Integer id, List<MenuVO> menuList) {
        List<MenuVO> childList = new ArrayList<>();
        for (MenuVO menuVO : menuList) {
            if (ObjectUtil.equal(id, menuVO.getParentId())) {
                childList.add(menuVO);
            }
        }
        //递归
        for (MenuVO menuVO : childList) {
            menuVO.setChildren(getChildrenList(menuVO.getId(), menuList));
        }
        if (childList.isEmpty()) {
            return new ArrayList<>();
        }
        return childList;
    }

    private MenuVO coverMenuVO(Menu menu){
        return MenuVO.builder()
                .id(menu.getId()).label(menu.getTitle())
                .parentId(menu.getParentId()).roleType(menu.getRole()).build();
    }

}
