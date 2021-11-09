package com.zhang.project.web.form;

import lombok.Data;

/**
 * @author Yaohang Zhang
 * @ClassName ExercisesForm
 * @description TODO
 * @date 2021-09-03 15:58
 */
@Data
public class ExercisesForm extends BasePageForm{


    private Integer id;

    private String openId;

    private String userOpenId;

    /**
     * 题目类型
     */
    private String exerciseType;

    /**
     * 关联类型(PROJECT 专项练习 EXAM_QUESTIONS 考试真题)
     */
    private String exercisesRelationType;

    /**
     * 关联Id
     */
    private String relationId;

    /**
     * 题目
     */
    private String content;

    /**
     * 选项A
     */
    private String optionA;

    /**
     * 选项B
     */
    private String optionB;

    /**
     * 选项C
     */
    private String optionC;

    /**
     * 选项D
     */
    private String optionD;

    /**
     * 答案
     */
    private String answer;

    /**
     * 解析
     */
    private String analysis;

    /**
     * 分数
     */
    private Integer score;

    /**
     * 等级
     */
    private Integer level;

    /**
     * 题目来源
     */
    private String origin;

    /**
     * 题目数量
     */
    private Integer count;

    private Integer classificationCount;
}
