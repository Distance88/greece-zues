package com.zhang.project.web.vo;

import com.zhang.project.dal.dataobject.BlogComment;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Yaohang Zhang
 * @ClassName BlogVO
 * @description TODO
 * @date 2021-10-11 10:31
 */
@Data
@Builder
public class BlogVO {

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
     * 浏览数
     */
    private Integer views;

    /**
     * 作者
     */
    private String author;

    private String createTime;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 博客评论
     */
    private List<BlogCommentVO> blogCommentVOList;

}
