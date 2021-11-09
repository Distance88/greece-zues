package com.zhang.project.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Yaohang Zhang
 * @ClassName UserStatusEnum
 * @description TODO
 * @date 2021-10-09 14:38
 */
@Getter
@AllArgsConstructor
public enum  UserStatusEnum {
    
    NORMAL("NORMAL","正常"),
    BAN("BAN","禁止");

    private String code;

    private String description;
}
