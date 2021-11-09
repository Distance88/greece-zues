package com.zhang.project.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Yaohang Zhang
 * @ClassName ExerciseTypeEnum
 * @description TODO
 * @date 2021-09-03 16:14
 */
@Getter
@AllArgsConstructor
public enum  ExerciseTypeEnum {

    CHOICE_QUESTION("CHOICE_QUESTION","选择题"),
    FILL_QUESTION("FILL_QUESTION","填空题"),
    OPERATION_QUESTION("OPERATION_QUESTION","操作题");

    private String code;

    private String description;
}
