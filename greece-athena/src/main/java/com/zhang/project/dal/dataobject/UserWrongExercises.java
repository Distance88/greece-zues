package com.zhang.project.dal.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.io.Serializable;

/**
 * 用户习题(UserWrongExercises)表实体类
 *
 * @author Distance
 * @since 2021-09-18 13:56:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserWrongExercises implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户Id
     */
    private String userOpenId;

    /**
     * 题目Id
     */
    private String exercisesId;

    /**
     * 做错次数
     */
    private Integer count;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String updateTime;


}
