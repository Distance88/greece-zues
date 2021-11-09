package com.zhang.project.web.rest;

import com.zhang.common.core.restful.CommonRestResult;
import com.zhang.common.core.template.RestBusinessTemplate;
import com.zhang.project.biz.manager.ClassificationExercisesManager;
import com.zhang.project.web.form.ClassificationForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 题目分类(ClassificationExercises)表控制层
 *
 * @author Distance
 * @since 2021-09-13 17:53:51
 */
@Api("题目分类")
@RestController
@RequestMapping("classification")
public class ClassificationExercisesController {
    /**
     * 服务对象
     */
    @Resource
    private ClassificationExercisesManager classificationExercisesManager;

    @ApiOperation(value = "查找所有分类",notes = "查找所有分类")
    @GetMapping("/getClassifications")
    public CommonRestResult getClassificationExercises() {
        return RestBusinessTemplate.execute(() -> classificationExercisesManager.getClassificationList());

    }

    @ApiOperation(value = "新增题目分类",notes = "新增题目分类")
    @PostMapping("/create")
    public CommonRestResult createClassificationExercises(@RequestBody @Valid ClassificationForm form){
        return RestBusinessTemplate.transaction(()->{
            classificationExercisesManager.createClassification(form);
            return null;
        });
    }

    @ApiOperation(value = "查找所有分类名称",notes = "查找所有分类名称")
    @GetMapping("/getClassificationName")
    public CommonRestResult getClassificationExercisesName(){
        return RestBusinessTemplate.execute(()->classificationExercisesManager.getClassificationExercisesName());
    }

}
