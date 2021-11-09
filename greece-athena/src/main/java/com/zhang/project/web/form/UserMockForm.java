package com.zhang.project.web.form;

import lombok.Data;

/**
 * @author Yaohang Zhang
 * @ClassName UserMockForm
 * @description TODO
 * @date 2021-09-29 13:45
 */
@Data
public class UserMockForm extends BasePageForm {

    private String name;

    private String status;

    /**
     * 唯一标识
     */
    private String openId;

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
}
