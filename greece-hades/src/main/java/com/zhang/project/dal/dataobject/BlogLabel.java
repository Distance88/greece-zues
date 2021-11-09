package com.zhang.project.dal.dataobject;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

/**
 * (BlogLabel)表实体类
 *
 * @author Distance
 * @since 2021-10-11 15:22:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BlogLabel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private Integer count;


}
