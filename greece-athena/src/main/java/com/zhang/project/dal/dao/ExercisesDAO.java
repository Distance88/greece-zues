package com.zhang.project.dal.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhang.project.dal.dataobject.Exercises;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 题目(Exercises)表数据库访问层
 *
 * @author Distance
 * @since 2021-09-14 16:18:23
 */
public interface ExercisesDAO extends BaseMapper<Exercises> {


    @Update("update exercises set level = #{level} where id = #{id}")
    int updateLevel(Integer level, Integer id);

    @Update("update exercises set status = !status where id = #{id}")
    int updateStatusById(Integer id);

    @Select("SELECT * FROM exercises WHERE exercises.id>=(RAND()*(SELECT MAX(id) FROM exercises)) AND relation_id = #{relationId} AND exercise_type = #{exerciseType} AND status = 1 LIMIT #{count};")
    List<Exercises> findByExerciseTypeRandom(String relationId, String exerciseType, Integer count);

}

