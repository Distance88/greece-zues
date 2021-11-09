package com.zhang.project.dal.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhang.project.dal.dataobject.BlogType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * (BlogType)表数据库访问层
 *
 * @author Distance
 * @since 2021-10-11 15:22:31
 */
public interface BlogTypeDAO extends BaseMapper<BlogType> {

    @Select("select * from blog_type where name = #{name}")
    BlogType findBlogTypeByName(String name);

    @Update("update blog_type set count = count + 1 where id = #{id}")
    int increaseCountById(Integer id);

    @Update("update blog_type set count = count - 1 where id = #{id} and count > 0")
    int decreaseCountById(Integer id);

}

