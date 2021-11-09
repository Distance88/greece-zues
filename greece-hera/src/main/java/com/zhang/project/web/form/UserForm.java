package com.zhang.project.web.form;

import lombok.Data;

/**
 * @author Yaohang Zhang
 * @ClassName UserForm
 * @description TODO
 * @date 2021-10-09 14:36
 */
@Data
public class UserForm extends BasePageForm{

    private Integer id;

    /**
     * 唯一标识
     */
    private String openId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

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
     * 状态
     */
    private String status;

    /**
     * 来源
     */
    private String origin;

    private String userOpenIdList;

    private String deptName;

    private String roleType;

}
