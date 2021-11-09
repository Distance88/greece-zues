package com.zhang.project.web.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Yaohang Zhang
 * @ClassName ClassificationForm
 * @description TODO
 * @date 2021-09-07 19:18
 */
@Data
public class ClassificationForm extends BasePageForm{

    @NotBlank(message = "题目类型不能为空")
    private String classificationType;

    @NotBlank(message = "分类名称不能为空")
    private String classificationName;
}
