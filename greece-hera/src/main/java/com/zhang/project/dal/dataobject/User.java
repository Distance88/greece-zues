package com.zhang.project.dal.dataobject;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

/**
 * 用户表(User)表实体类
 *
 * @author Distance
 * @since 2021-10-19 15:59:36
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
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

    private String userInfo;

    /**
     * 状态
     */
    private String status;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String updateTime;


}
