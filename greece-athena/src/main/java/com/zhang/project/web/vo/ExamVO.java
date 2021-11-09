package com.zhang.project.web.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author Yaohang Zhang
 * @ClassName ExamVO
 * @description TODO
 * @date 2021-09-26 17:17
 */
@Data
@Builder
public class ExamVO {

    /**
     * 专项Id
     */
    private String openId;

    /**
     * 考试标题
     */
    private String title;

    /**
     * 分类题目数
     */
    private Integer questionCount;

    /**
     * 分数（EXAM_QUESTION）
     */
    private Integer examScore;

    /**
     * 考试开始时间
     */
    private String examBegin;

    /**
     * 考试结束时间
     */
    private String examEnd;

    /**
     * 考试状态
     */
    private String examStatus;

    /**
     * 状态
     */
    private Boolean status;
}
