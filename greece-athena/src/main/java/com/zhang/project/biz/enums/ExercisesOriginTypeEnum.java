package com.zhang.project.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExercisesOriginTypeEnum {

    WRONGANDNEW("WRONGANDNEW","错误加新题"),
    NEW("NEW","只出新题"),
    WRONG("WRONG","只出错题"),
    ALL("ALL","不限来源");

    private String code;

    private String description;

    public ExercisesOriginTypeEnum getExercisesOriginTypeEnum(String code){
        return ExercisesOriginTypeEnum.valueOf(code);
    }
}
