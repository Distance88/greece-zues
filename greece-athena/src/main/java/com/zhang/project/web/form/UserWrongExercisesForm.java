package com.zhang.project.web.form;

import lombok.Data;

import java.util.List;

/**
 * @author Yaohang Zhang
 * @ClassName UserWrongExercisesForm
 * @description TODO
 * @date 2021-09-18 14:01
 */
@Data
public class UserWrongExercisesForm {

    private String userOpenId;

    private List<String> wrongExercisesIdList;
}
