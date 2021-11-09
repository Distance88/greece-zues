package com.zhang.project.biz.manager;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zhang.common.core.exception.BizCoreException;
import com.zhang.project.dal.dao.DeptDAO;
import com.zhang.project.dal.dataobject.Dept;
import com.zhang.project.web.vo.DeptVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yaohang Zhang
 * @ClassName DeptManager
 * @description TODO
 * @date 2021-10-19 17:24
 */
@Service
public class DeptManager {

    @Resource
    private DeptDAO deptDAO;

    @Resource
    private UserManager userManager;

    public DeptVO getDeptList(){
        List<Dept> deptList = deptDAO.selectList(null);
        if(deptList.isEmpty()){
            return null;
        }
        Dept firstDept = deptList.get(0);
        DeptVO firstVO = DeptVO.builder().build();
        firstVO.setLabel(firstDept.getName());
        firstVO.setUserOpenId(firstDept.getUserOpenIdList());
        List<DeptVO> children = deptList.stream().filter(dept -> !StringUtils.equals(dept.getOpenId(), firstDept.getOpenId()))
                .map(dept -> coverDeptVO(dept)).collect(Collectors.toList());
        firstVO.setChildren(children);
        return firstVO;
    }

    public void updateDeptByDeptName(String deptName,String userOpenId){

        Dept dept = deptDAO.findByDeptName(deptName);
        if(ObjectUtil.isNull(dept)){
            throw new BizCoreException("没有找到该年级");
        }
        List<String> userOpenIdList = JSONArray.parseArray(dept.getUserOpenIdList(), String.class);
        userOpenIdList.add(userOpenId);
        int update = deptDAO.updateUserOpenIdById(dept.getId(), JSONArray.toJSONString(userOpenIdList));
        if(update == 0){
            throw new BizCoreException("修改年级失败");
        }
        dept = deptDAO.findByDeptName("南工社团");
        if(ObjectUtil.isNull(dept)){
            throw new BizCoreException("没有找到该年级");
        }
        userOpenIdList = JSONArray.parseArray(dept.getUserOpenIdList(), String.class);
        userOpenIdList.add(userOpenId);
        update = deptDAO.updateUserOpenIdById(dept.getId(), JSONArray.toJSONString(userOpenIdList));
        if(update == 0){
            throw new BizCoreException("修改年级失败");
        }
    }

    private DeptVO coverDeptVO(Dept dept){
        List<String> userOpenIdList = JSONArray.parseArray(dept.getUserOpenIdList(), String.class);
        List<DeptVO> children = new ArrayList<>();
        for(String userOpenId:userOpenIdList){
            String name = userManager.findNameByUserOpenId(userOpenId);
            List<String> openIdList = new ArrayList<>();
            openIdList.add(userOpenId);
            children.add(DeptVO.builder().label(name).userOpenId(JSONArray.toJSONString(openIdList)).build());
        }
        return  DeptVO.builder().label(dept.getName()).children(children).userOpenId(dept.getUserOpenIdList()).build();


    }
}
