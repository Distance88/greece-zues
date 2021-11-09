package com.zhang.project.web.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author JiaHao-Li
 * @date 2019年12月11日 2:36 PM
 */
@Data
public class BasePageForm {

    @NotNull(message = "页码不能为空")
    protected Integer current;

    @NotNull(message = "每页大小不能为空")
    protected Integer size;
}
