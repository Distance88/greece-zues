package com.zhang.project.web.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Yaohang Zhang
 * @ClassName ExamDetailVO
 * @description TODO
 * @date 2021-09-27 17:44
 */
@Data
@Builder
public class ExamDetailVO {

    private List<ExercisesVO> choiceQuestion;

    private List<ExercisesVO> fillQuestion;

    private List<ExercisesVO> operationQuestion;

}
