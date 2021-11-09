package com.zhang.project.dal.dataobject;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

/**
 * 菜单权限表(Menu)表实体类
 *
 * @author Distance
 * @since 2021-10-09 14:56:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    @TableId(type = IdType.AUTO)
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
     * 菜单类型（M目录 C菜单 F按钮）
     */
    private String type;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 菜单状态（1显示 0隐藏）
     */
    private Boolean status;

    /**
     * 角色标识
     */
    private String role;

    /**
     * 父菜单ID
     */
    private Integer parentId;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String updateTime;


}
