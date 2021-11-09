package com.zhang.project.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Yaohang Zhang
 * @ClassName ClassificationTypeEnum
 * @description TODO
 * @date 2021-09-03 15:54
 */
@Getter
@AllArgsConstructor
public enum ClassificationTypeEnum {

    PROJECT("PROJECT","专项练习"),
    EXAM_QUESTIONS("EXAM_QUESTIONS","考试真题");

    private String code;

    private String description;
}
