package com.zhang.project.web.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Yaohang Zhang
 * @ClassName InfoVO
 * @description TODO
 * @date 2021-10-11 19:13
 */
@Data
@Builder
public class InfoVO {

    private Integer id;

    /**
     * 唯一openId
     */
    private String openId;

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

}
