package com.zhang.project.web.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Yaohang Zhang
 * @ClassName BasePageForm
 * @description TODO
 * @date 2021-10-11 10:17
 */
@Data
public class BasePageForm {

    @NotNull(message = "页码不能为空")
    protected Integer current;

    @NotNull(message = "每页大小不能为空")
    protected Integer size;
}
