package com.zhang.project.web.form;

import lombok.Data;

import java.util.List;

/**
 * @author Yaohang Zhang
 * @ClassName RoleMenuForm
 * @description TODO
 * @date 2021-10-21 10:23
 */
@Data
public class RoleMenuForm {

    private String roleType;

    private List<Integer> menuIdList;
}
