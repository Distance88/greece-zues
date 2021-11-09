package com.zhang.project.dal.dataobject;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

/**
 * (Info)表实体类
 *
 * @author Distance
 * @since 2021-10-11 18:51:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Info implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
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
     * 内容
     */
    private String content;

    /**
     * md格式
     */
    private String mdContent;

    /**
     * 简介
     */
    private String digest;

    /**
     * 类型
     */
    private String style;

    /**
     * 作者
     */
    private String author;

    /**
     * 访问量
     */
    private Integer views;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String updateTime;


}
