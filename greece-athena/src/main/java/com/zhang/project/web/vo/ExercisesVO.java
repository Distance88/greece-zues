package com.zhang.project.web.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author Yaohang Zhang
 * @ClassName ExercisesVO
 * @description TODO
 * @date 2021-09-03 15:58
 */
@Data
@Builder
public class ExercisesVO {

    private Integer id;

    private String openId;

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
     * 学科类型
     */
    private String classificationName;

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
     * 排序Id 升序
     */
    private Integer sortId;

    /**
     * 状态
     */
    private Boolean status;

    /**
     * 分数
     */
    private Integer score;

    /**
     * 等级标识
     */
    private Integer level;

}
