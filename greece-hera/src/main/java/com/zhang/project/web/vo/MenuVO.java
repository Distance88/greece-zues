package com.zhang.project.web.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Yaohang Zhang
 * @ClassName MenuVO
 * @description TODO
 * @date 2021-09-08 17:00
 */
@Data
@Builder
public class MenuVO {

    private Integer id;

    private Integer parentId;

    private String label;

    private List<MenuVO> children;

    private String roleType;
}
