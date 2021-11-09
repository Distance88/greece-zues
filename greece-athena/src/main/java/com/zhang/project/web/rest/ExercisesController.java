package com.zhang.project.web.rest;

import com.zhang.common.core.restful.CommonRestResult;
import com.zhang.common.core.template.RestBusinessTemplate;
import com.zhang.project.biz.manager.ExercisesManager;
import com.zhang.project.web.form.ExercisesForm;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 题目(Exercises)表控制层
 *
 * @author Distance
 * @since 2021-09-14 16:18:19
 */
@RestController
@RequestMapping("exercises")
public class ExercisesController {
    /**
     * 服务对象
     */
    @Resource
    private ExercisesManager exercisesManager;


    @ApiOperation(value = "获取专项练习题目")
    @GetMapping("/practice")
    public CommonRestResult getExercisesByProject(ExercisesForm form){
        return RestBusinessTemplate.execute(()->exercisesManager.getExercisesByProject(form));
    }

    @ApiOperation("查找所有题目")
    @GetMapping("/list")
    public CommonRestResult getExercisesList(ExercisesForm form){
        return RestBusinessTemplate.execute(()->exercisesManager.getExercisesList(form));
    }

    @ApiOperation(value = "添加题目",notes = "添加题目")
    @PostMapping("/create")
    public CommonRestResult createExercises(@RequestBody ExercisesForm form){
        return RestBusinessTemplate.transaction(()->{
            exercisesManager.createExercises(form);
            return null;
        });
    }

    @ApiOperation(value = "修改题目状态",notes = "修改题目状态")
    @PostMapping("/updateStatus")
    public CommonRestResult updateStatus(@RequestParam("id") Integer id){
        return RestBusinessTemplate.transaction(()->{
            exercisesManager.updateStatus(id);
            return null;
        });
    }

    @ApiOperation(value = "修改题目",notes = "修改题目")
    @PostMapping("/update")
    public CommonRestResult update(@RequestBody ExercisesForm form){
        return RestBusinessTemplate.transaction(()->{
            exercisesManager.update(form);
            return null;
        });
    }
}
