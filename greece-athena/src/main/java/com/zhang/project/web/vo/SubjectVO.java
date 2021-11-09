package com.zhang.project.web.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author Yaohang Zhang
 * @ClassName SubjectVO
 * @description TODO
 * @date 2021-09-03 14:19
 */
@Data
@Builder
public class SubjectVO {

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

    private Boolean status;
}
