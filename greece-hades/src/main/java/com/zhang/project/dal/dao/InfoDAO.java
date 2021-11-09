package com.zhang.project.dal.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhang.project.dal.dataobject.Info;
import org.apache.ibatis.annotations.Update;

/**
 * (Info)表数据库访问层
 *
 * @author Distance
 * @since 2021-10-11 18:51:10
 */
public interface InfoDAO extends BaseMapper<Info> {

    @Update("update info set views = #{views} where id = #{id}")
    int updateViewById(Integer id,Integer views);
}

