package com.zhang.project.dal.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhang.project.dal.dataobject.Menu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author Distance
 * @since 2021-10-09 14:56:21
 */
public interface MenuDAO extends BaseMapper<Menu> {


    @Update("update menu set role = #{role} where id = #{id}")
    int updateRoleById(@Param("id")Integer id,@Param("role")String role);
}

