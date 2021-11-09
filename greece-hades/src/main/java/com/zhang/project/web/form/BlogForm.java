package com.zhang.project.web.form;

import lombok.Data;

/**
 * @author Yaohang Zhang
 * @ClassName BlogForm
 * @description TODO
 * @date 2021-10-11 10:18
 */
@Data
public class BlogForm extends BasePageForm{

    private Integer id;
    /**
     * 唯一openId
     */
    private String openId;

    /**
     * 用户openId
     */
    private String userOpenId;

    /**
     * 标题
     */
    private String title;

    /**
     * 简介
     */
    private String digest;

    /**
     * 内容
     */
    private String content;

    /**
     * md内容
     */
    private String mdContent;

    /**
     * 插画链接
     */
    private String charts;

    /**
     * 类型
     */
    private String type;

    /**
     * 标签
     */
    private String label;

    /**
     * 格式
     */
    private String style;

    /**
     * 作者
     */
    private String author;


    /**
     * 角色类型
     */
    private String roleType;
}
