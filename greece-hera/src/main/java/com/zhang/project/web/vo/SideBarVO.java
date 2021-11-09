package com.zhang.project.web.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Yaohang Zhang
 * @ClassName SideBarVO
 * @description TODO
 * @date 2021-09-08 17:03
 */
@Data
@Builder
public class SideBarVO {

    private Integer id;
    /**
     * 菜单名称
     */
    private String title;

    /**
     * 请求地址
     */
    private String path;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 父菜单ID
     */
    private Integer parentId;

    /**
     * 子菜单
     */
    private List<SideBarVO> children;

}
