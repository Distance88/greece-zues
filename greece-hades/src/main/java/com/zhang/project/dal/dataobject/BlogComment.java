package com.zhang.project.dal.dataobject;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

/**
 * (BlogComment)表实体类
 *
 * @author Distance
 * @since 2021-10-11 10:42:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BlogComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 唯一openId
     */
    private String openId;

    /**
     * 博客openId
     */
    private String blogOpenId;

    /**
     * 回复openId
     */
    private String replyOpenId;

    /**
     * 名字
     */
    private String name;

    /**
     * 内容
     */
    private String content;

    /**
     * 回复名字
     */
    private String replyName;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String updateTime;


}
