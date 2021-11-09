package com.zhang.project.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OriginEnum {

    SERVICE("SERVICE","SERVICE"),
    ADMIN("ADMIN","ADMIN");

    private String code;

    private String description;
}
