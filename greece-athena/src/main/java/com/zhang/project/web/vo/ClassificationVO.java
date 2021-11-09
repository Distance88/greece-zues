package com.zhang.project.web.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author Yaohang Zhang
 * @ClassName ClassificationVO
 * @description TODO
 * @date 2021-09-07 19:25
 */
@Data
@Builder
public class ClassificationVO {

    /**
     * 专项Id
     */
    private String openId;

    /**
     * 分类名
     */
    private String classificationName;

    /**
     * 分类题目数
     */
    private Integer classificationCount;

    /**
     * 状态
     */
    private Boolean status;
}
