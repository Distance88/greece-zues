package com.zhang.project.web.form;

import lombok.Data;

/**
 * @author Yaohang Zhang
 * @ClassName InfoForm
 * @description TODO
 * @date 2021-10-11 18:52
 */
@Data
public class InfoForm extends BasePageForm{

    /**
     * 唯一openId
     */
    private String openId;

    /**
     * 标题
     */
    private String title;

    /**
     * 类型
     */
    private String style;

    /**
     * 作者
     */
    private String author;
}
