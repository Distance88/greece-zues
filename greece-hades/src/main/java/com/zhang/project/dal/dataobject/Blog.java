package com.zhang.project.dal.dataobject;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

/**
 * (Blog)表实体类
 *
 * @author Distance
 * @since 2021-10-11 10:15:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
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

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String updateTime;


}
