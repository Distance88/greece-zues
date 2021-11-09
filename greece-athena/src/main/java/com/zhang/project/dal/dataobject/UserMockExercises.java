package com.zhang.project.dal.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.io.Serializable;

/**
 * (UserMockExercises)表实体类
 *
 * @author Distance
 * @since 2021-09-18 13:56:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserMockExercises implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 唯一标识
     */
    private String openId;

    /**
     * 用户openId
     */
    private String userOpenId;

    /**
     * 标题
     */
    private String title;

    /**
     * 题目分类类型
     */
    private String exercisesRelationType;

    /**
     * 科目关联id
     */
    private String relationId;

    /**
     * 记录详情
     */
    private String mockDetail;

    /**
     * 题目数量
     */
    private Integer exercisesCount;

    /**
     * 正确题目数量
     */
    private Integer exercisesPassCount;

    /**
     * 得分
     */
    private Integer scores;

    /**
     * 使用时间
     */
    private String time;

    /**
     * 试卷状态
     */
    private String status;
    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String updateTime;


}
