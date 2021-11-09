package com.zhang.project.dal.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhang.project.dal.dataobject.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 用户表(User)表数据库访问层
 *
 * @author Distance
 * @since 2021-10-09 14:16:08
 */
public interface UserDAO extends BaseMapper<User> {

    @Select("select * from user where open_id = #{userOpenId}")
    User findByUserOpenId(String userOpenId);

    @Update("update user set avatar = #{avatar} where id = #{id}")
    int updateAvatarById(@Param("id") Integer id,@Param("avatar") String avatar);

    @Update("update user set status = #{status} where open_id = #{openId}")
    int updateStatusByOpenId(@Param("openId") String openId,@Param("status") String status);

    @Update("update user set user_info = #{userInfo} where open_id = #{openId}")
    int updateUserInfoByOpenId(@Param("openId") String openId,@Param("userInfo") String userInfo);
}


