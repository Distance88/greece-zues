package com.zhang.project.dal.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhang.project.dal.dataobject.BlogLabel;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * (BlogLabel)表数据库访问层
 *
 * @author Distance
 * @since 2021-10-11 15:22:40
 */
public interface BlogLabelDAO extends BaseMapper<BlogLabel> {

    @Select("select * from blog_label where name = #{name}")
    BlogLabel findBlogLabelByName(String name);

    @Update("update blog_label set count = count + 1 where id = #{id}")
    int increaseCountById(Integer id);

    @Update("update blog_label set count = count - 1 where id = #{id} and count > 0")
    int decreaseCountById(Integer id);
}

