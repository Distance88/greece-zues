package com.zhang.project.dal.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.io.Serializable;

/**
 * 题目(Exercises)表实体类
 *
 * @author Distance
 * @since 2021-09-14 16:18:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Exercises implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String openId;

    /**
     * 题目类型
     */
    private String exerciseType;

    /**
     * 关联类型(PROJECT 专项练习 EXAM_QUESTIONS 考试真题)
     */
    private String exercisesRelationType;

    /**
     * 关联Id
     */
    private String relationId;

    /**
     * 题目
     */
    private String content;

    /**
     * 选项A
     */
    private String optionA;

    /**
     * 选项B
     */
    private String optionB;

    /**
     * 选项C
     */
    private String optionC;

    /**
     * 选项D
     */
    private String optionD;

    /**
     * 答案
     */
    private String answer;

    /**
     * 解析
     */
    private String analysis;

    /**
     * 分数
     */
    private Integer score;

    /**
     * 难度等级
     */
    private Integer level;

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
