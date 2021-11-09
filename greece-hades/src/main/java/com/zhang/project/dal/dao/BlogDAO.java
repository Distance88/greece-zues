package com.zhang.project.dal.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhang.project.dal.dataobject.Blog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * (Blog)表数据库访问层
 *
 * @author Distance
 * @since 2021-10-11 10:09:08
 */
public interface BlogDAO extends BaseMapper<Blog> {


    @Select("select * from blog order by views desc limit 5")
    List<Blog> findFiveBlogByViewsDesc();

    @Update("update blog set views = #{views} where id = #{id}")
    int updateViewById(@Param("id") Integer id, @Param("views") Integer views);
}

