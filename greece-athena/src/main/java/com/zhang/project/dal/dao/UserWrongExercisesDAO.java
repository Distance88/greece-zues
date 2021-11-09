package com.zhang.project.dal.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhang.project.dal.dataobject.UserWrongExercises;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户习题(UserWrongExercises)表数据库访问层
 *
 * @author Distance
 * @since 2021-09-18 13:50:39
 */
public interface UserWrongExercisesDAO extends BaseMapper<UserWrongExercises> {


    @Select("select * from user_wrong_exercises where user_open_id = #{userOpenId} and exercises_id = #{exercisesId}")
    UserWrongExercises findByUserOpenIdAndExercisesId(String userOpenId, String exercisesId);

    @Select("select exercises_id from user_wrong_exercises where user_open_id = #{userOpenId}")
    List<String> findExercisesIdByUserOpenId(String userOpenId);
}

