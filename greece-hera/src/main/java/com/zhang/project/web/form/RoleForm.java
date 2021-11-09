package com.zhang.project.web.form;

import lombok.Data;

/**
 * @author Yaohang Zhang
 * @ClassName RoleForm
 * @description TODO
 * @date 2021-10-20 17:28
 */
@Data
public class RoleForm extends BasePageForm{

    private Integer id;

    /**
     * 唯一标识
     */
    private String openId;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色code
     */
    private String type;

    /**
     * 角色状态（1正常 0停用）
     */
    private Boolean status;

}
