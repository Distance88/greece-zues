package com.zhang.project.dal.dataobject;

import lombok.*;

import java.io.Serializable;

/**
 * 试卷表(Exam)表实体类
 *
 * @author Distance
 * @since 2021-09-26 17:14:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Exam implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 专项Id
     */
    private String openId;

    /**
     * 考试标题
     */
    private String title;

    /**
     * 关联ID
     */
    private String relationId;

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

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String updateTime;


}
