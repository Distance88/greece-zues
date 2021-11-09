package com.zhang.project.web.rest;

import com.zhang.common.core.restful.CommonRestResult;
import com.zhang.common.core.template.RestBusinessTemplate;
import com.zhang.project.biz.manager.ExamManager;
import com.zhang.project.web.form.ExamForm;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 试卷表(Exam)表控制层
 *
 * @author Distance
 * @since 2021-09-26 17:14:12
 */
@RestController
@RequestMapping("exam")
public class ExamController {
    /**
     * 服务对象
     */
    @Resource
    private ExamManager examManager;

    @ApiOperation(value = "获取考试真题信息列表",notes = "获取考试真题信息列表")
    @GetMapping("/infoList")
    public CommonRestResult infoList(@RequestParam("current") Integer current){
        return RestBusinessTemplate.execute(()->examManager.infoList(current));
    }

    @ApiOperation(value = "考试信息详情",notes = "考试信息详情")
    @GetMapping("/detail")
    public CommonRestResult detail(@RequestParam("openId") String openId, @RequestParam("userOpenId")String userOpenId){
        return RestBusinessTemplate.execute(()->examManager.examDetail(openId,userOpenId));
    }

    @ApiOperation(value = "新增试卷",notes = "新增试卷")
    @PostMapping("create")
    public CommonRestResult create(@RequestBody ExamForm form){
        return RestBusinessTemplate.transaction(()->{
            examManager.createExam(form);
            return null;
        });
    }

    @ApiOperation(value = "试卷列表",notes = "试卷列表")
    @GetMapping("list")
    public CommonRestResult list(ExamForm form){
        return RestBusinessTemplate.execute(()-> examManager.getList(form));
    }
}
