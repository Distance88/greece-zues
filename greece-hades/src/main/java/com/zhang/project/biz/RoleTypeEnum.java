package com.zhang.project.biz;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
@AllArgsConstructor
public enum RoleTypeEnum {

    ADMINISTRATOR("ADMINISTRATOR","超级管理员"),
    USER("USER","普通用户");

    private String code;

    private String description;

    public static RoleTypeEnum getRecord(String code){

        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (RoleTypeEnum record : RoleTypeEnum.values()) {
            if (StringUtils.equals(record.getCode(), code)) {
                return record;
            }
        }
        return null;
    }

}
