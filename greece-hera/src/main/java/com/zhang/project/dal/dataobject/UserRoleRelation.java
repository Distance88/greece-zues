package com.zhang.project.dal.dataobject;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

/**
 * 用户角色关联表(UserRoleRelation)表实体类
 *
 * @author Distance
 * @since 2021-10-09 14:56:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRoleRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户唯一标识
     */
    private String userOpenId;

    /**
     * 角色唯一标识
     */
    private String roleOpenId;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String updateTime;


}
