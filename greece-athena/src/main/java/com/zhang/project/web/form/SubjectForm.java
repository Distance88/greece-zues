package com.zhang.project.web.form;

import lombok.Data;

/**
 * @author Yaohang Zhang
 * @ClassName SubjectForm
 * @description TODO
 * @date 2021-09-03 14:12
 */
@Data
public class SubjectForm extends BasePageForm{


    /**
     * 学科Id
     */
    private String openId;
    /**
     * 学科名
     */
    private String subjectName;

    /**
     * 考试大纲
     */
    private String outlineUrl;

    /**
     * 排序Id 升序
     */
    private Integer sortId;

    /**
     * 状态
     */
    private Boolean status;

}
