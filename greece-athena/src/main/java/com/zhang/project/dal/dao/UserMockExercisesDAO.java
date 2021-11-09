package com.zhang.project.dal.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhang.project.dal.dataobject.UserMockExercises;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * (UserMockExercises)表数据库访问层
 *
 * @author Distance
 * @since 2021-09-18 13:52:14
 */
public interface UserMockExercisesDAO extends BaseMapper<UserMockExercises> {


    @Select("select * from user_mock_exercises where user_open_id = #{userOpenId} and relation_id = #{relationId}")
    List<UserMockExercises> findByUserOpenIdAndExamOpenId(String userOpenId, String relationId);
}

