package com.zhang.project.dal.dataobject;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

/**
 * (UserBlogLogRecord)表实体类
 *
 * @author Distance
 * @since 2021-10-15 13:56:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserBlogLogRecord implements Serializable {

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
     * 博客openId
     */
    private String blogOpenId;

    /**
     * 博客详情
     */
    private String detail;

    /**
     * 操作记录
     */
    private String operation;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String updateTime;


}
