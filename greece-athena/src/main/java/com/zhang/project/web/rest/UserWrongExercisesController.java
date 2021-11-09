package com.zhang.project.web.rest;

import com.zhang.common.core.restful.CommonRestResult;
import com.zhang.common.core.template.RestBusinessTemplate;
import com.zhang.project.biz.manager.UserWrongExercisesManager;
import com.zhang.project.web.form.UserWrongExercisesForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户习题(UserWrongExercises)表控制层
 *
 * @author Distance
 * @since 2021-09-18 13:50:35
 */
@Api("userWrongExercises")
@RestController
@RequestMapping("userWrongExercises")
public class UserWrongExercisesController {
    /**
     * 服务对象
     */
    @Resource
    private UserWrongExercisesManager userWrongExercisesManager;

    @ApiOperation(value = "创建用户错题记录",notes = "创建用户错题记录")
    @PostMapping("/create")
    public CommonRestResult create(@RequestBody UserWrongExercisesForm form){
        return RestBusinessTemplate.transaction(()->{
            userWrongExercisesManager.create(form);
            return null;
        });
    }

}
