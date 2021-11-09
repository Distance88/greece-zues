package com.zhang.project.web.form;

import lombok.Data;

import java.util.Date;

/**
 * @author Yaohang Zhang
 * @ClassName ExamForm
 * @description TODO
 * @date 2021-09-24 10:21
 */
@Data
public class ExamForm extends BasePageForm {

    private String relationId;

    private String title;

    private ChoiceQuestion choiceQuestion;

    private FillQuestion fillQuestion;

    private OperationQuestion operationQuestion;


    @Data
    public static class ChoiceQuestion{

        private Integer count;

        private Integer score;
    }

    @Data
    public static class FillQuestion{

        private Integer count;

        private Integer score;
    }

    @Data
    public static class OperationQuestion{

        private Integer count;

        private Integer score;
    }

    /**
     * 分类题目数
     */
    private Integer questionCount;

    /**
     * 分数
     */
    private Integer examScore;

    /**
     * 考试时间
     */
    private Integer examTime;

    /**
     * 考试开始时间
     */
    private Date examBegin;

    /**
     * 考试结束时间
     */
    private Date examEnd;

    /**
     * 考试状态
     */
    private String examStatus;

    private Boolean status;
}
