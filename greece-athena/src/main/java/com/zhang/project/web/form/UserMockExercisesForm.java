package com.zhang.project.web.form;

import lombok.Data;

/**
 * @author Yaohang Zhang
 * @ClassName UserMockExercisesForm
 * @description TODO
 * @date 2021-09-18 14:00
 */
@Data
public class UserMockExercisesForm extends BasePageForm{

    /**
     * 用户openId
     */
    private String userOpenId;

    /**
     * 标题
     */
    private String title;
    /**
     * 题目分类类型
     */
    private String exercisesRelationType;

    /**
     * 科目关联id
     */
    private String relationId;

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
}
