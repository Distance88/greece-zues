package com.zhang.project.dal.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.io.Serializable;

/**
 * 题目分类(ClassificationExercises)表实体类
 *
 * @author Distance
 * @since 2021-09-13 17:53:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClassificationExercises implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 专项Id
     */
    private String openId;

    /**
     * 分类名
     */
    private String classificationName;

    /**
     * 分类题目数
     */
    private Integer classificationCount;

    /**
     * 状态
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
