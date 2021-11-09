package com.zhang.project.dal.dataobject;

import java.io.Serializable;

import lombok.*;

/**
 * 部门表(Dept)表实体类
 *
 * @author Distance
 * @since 2021-10-19 17:16:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Dept implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 唯一标识
     */
    private String openId;

    /**
     * 名称
     */
    private String name;

    /**
     * 包含openId
     */
    private String userOpenIdList;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String updateTime;


}
