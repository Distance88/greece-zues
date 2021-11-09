package com.zhang.project.dal.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhang.project.dal.dataobject.ClassificationExercises;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 题目分类(ClassificationExercises)表数据库访问层
 *
 * @author Distance
 * @since 2021-09-13 17:53:53
 */
public interface ClassificationExercisesDAO extends BaseMapper<ClassificationExercises> {

    @Select("select classification_name from classification_exercises where open_id = #{openId}")
    String findClassificationNameByOpenId(String openId);

    @Select("select * from classification_exercises where classification_type = #{classificationType} and classification_name = #{classificationName}")
    ClassificationExercises findByClassificationTypeAndClassificationName(String classificationType,String classificationName);

    @Select("select classification_name from classification_exercises where open_id = #{relationId}")
    String findClassificationNameByRelationId(String relationId);

    @Update("update classification_exercises set classification_count = classification_count + 1 where open_id = #{openId}")
    int increaseClassificationCountByOpenId(String openId);
}

