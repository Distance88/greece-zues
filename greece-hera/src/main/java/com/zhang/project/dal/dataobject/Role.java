package com.zhang.project.dal.dataobject;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

/**
 * 角色信息表(Role)表实体类
 *
 * @author Distance
 * @since 2021-10-09 14:56:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @TableId(type = IdType.AUTO)
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

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String updateTime;


}
