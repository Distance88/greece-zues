package com.zhang.project.web.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author Yaohang Zhang
 * @ClassName UserMockExercisesVO
 * @description TODO
 * @date 2021-09-18 17:23
 */
@Data
@Builder
public class UserMockExercisesVO {

    /**
     * 唯一标识
     */
    private String openId;

    /**
     * 用户openId
     */
    private String userOpenId;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 标题
     */
    private String title;

    /**
     * 记录详情
     */
    private String mockDetail;

    /**
     * 题目数量
     */
    private Integer exercisesCount;

    /**
     * 正确题目数量
     */
    private Integer exercisesPassCount;

    /**
     * 得分
     */
    private Integer scores;

    /**
     * 使用时间
     */
    private String time;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 试卷状态
     */
    private String status;
}
