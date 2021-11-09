package com.zhang.project.web.rest;

import com.zhang.common.core.restful.CommonRestResult;
import com.zhang.common.core.template.RestBusinessTemplate;
import com.zhang.project.biz.manager.UserMockExercisesManager;
import com.zhang.project.dal.dao.UserMockExercisesDAO;
import com.zhang.project.web.form.UserMockExercisesForm;
import com.zhang.project.web.form.UserMockForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (UserMockExercises)表控制层
 *
 * @author Distance
 * @since 2021-09-18 13:52:10
 */
@Api("userMockExercises")
@RestController
@RequestMapping("userMockExercises")
public class UserMockExercisesController {
    /**
     * 服务对象
     */
    @Resource
    private UserMockExercisesManager userMockExercisesManager;

    @Resource
    private UserMockExercisesDAO userMockExercisesDAO;


    @ApiOperation(value = "创建用户题目记录",notes = "创建用户题目记录")
    @PostMapping("/create")
    public CommonRestResult create(@RequestBody UserMockExercisesForm form){
        return RestBusinessTemplate.transaction(()->{
            userMockExercisesManager.create(form);
            return null;
        });
    }

    @ApiOperation(value = "用户题目记录列表",notes = "用户题目记录列表")
    @GetMapping("/list/v1")
    public CommonRestResult list(UserMockExercisesForm form){
        return RestBusinessTemplate.execute(()-> userMockExercisesManager.list(form.getUserOpenId(),form.getExercisesRelationType(),form.getCurrent()));
    }

    @ApiOperation(value = "用户题目记录详情",notes = "用户题目记录详情")
    @GetMapping("/detail")
    public CommonRestResult detail(Integer id){
        return RestBusinessTemplate.execute(()-> userMockExercisesDAO.selectById(id).getMockDetail());
    }

    @ApiOperation(value = "用户考试记录",notes = "用户考试记录")
    @GetMapping("/list")
    public CommonRestResult list(UserMockForm form){
        return RestBusinessTemplate.execute(()->userMockExercisesManager.list(form));
    }

    @ApiOperation(value = "批阅试卷",notes = "批阅试卷")
    @PostMapping("/review")
    public CommonRestResult review(@RequestBody UserMockForm form){
        return RestBusinessTemplate.transaction(()->{
            userMockExercisesManager.review(form);
            return null;
        });
    }
}
