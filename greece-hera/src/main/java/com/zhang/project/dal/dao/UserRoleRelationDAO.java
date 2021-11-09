package com.zhang.project.dal.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhang.project.dal.dataobject.UserRoleRelation;
import org.apache.ibatis.annotations.Select;

/**
 * 用户角色关联表(UserRoleRelation)表数据库访问层
 *
 * @author Distance
 * @since 2021-10-09 14:56:36
 */
public interface UserRoleRelationDAO extends BaseMapper<UserRoleRelation> {


    @Select("select role_open_id from user_role_relation where user_open_id = #{userOpenId} order by id asc limit 1")
    String findFirstRoleOpenIdByUserOpenId(String userOpenId);
}

