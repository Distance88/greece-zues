package com.zhang.project.dal.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhang.project.dal.dataobject.Role;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author Distance
 * @since 2021-10-09 14:56:01
 */
public interface RoleDAO extends BaseMapper<Role> {


    @Select("select * from role where type = #{type} and status = 1")
    Role findByType(String type);

    @Update("update role set status = !status where id = #{id}")
    int updateStatusById(Integer id);
}
