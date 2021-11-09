package com.zhang.project.web.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Yaohang Zhang
 * @ClassName UserVO
 * @description TODO
 * @date 2021-09-08 15:47
 */
@Data
@Builder
public class UserVO {

    /**
     * 唯一标识
     */
    private String openId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 身份证姓名
     */
    private String name;

    /**
     * 所在班级
     */
    private String clazz;

    /**
     * 学号
     */
    private String sno;

    /**
     * 性别
     */
    private String sex;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 角色类型
     */
    private String roleType;

    /**
     * 状态
     */
    private String status;

    private String userInfo;

    private String description;

}
