package com.zhang.project.dal.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhang.project.dal.dataobject.Dept;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 部门表(Dept)表数据库访问层
 *
 * @author Distance
 * @since 2021-10-19 16:00:31
 */
public interface DeptDAO extends BaseMapper<Dept> {


    @Select("select * from dept where name = #{dept}")
    Dept findByDeptName(String deptName);

    @Update("update dept set user_open_id_list = #{userOpenIdList} where id = #{id}")
    int updateUserOpenIdById(@Param("id") Integer id,@Param("userOpenIdList") String userOpenIdList);
}

