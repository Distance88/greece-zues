package com.zhang.project.web.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Yaohang Zhang
 * @ClassName BlogCommentVO
 * @description TODO
 * @date 2021-10-11 10:43
 */
@Data
@Builder
public class BlogCommentVO {

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

    private List<BlogCommentVO> children;

}
