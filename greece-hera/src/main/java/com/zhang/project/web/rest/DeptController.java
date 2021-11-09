package com.zhang.project.web.rest;

import com.zhang.common.core.restful.CommonRestResult;
import com.zhang.common.core.template.RestBusinessTemplate;
import com.zhang.project.biz.manager.DeptManager;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 部门表(Dept)表控制层
 *
 * @author Distance
 * @since 2021-10-19 19:28:16
 */
@RestController
@RequestMapping("/**/user/dept")
public class DeptController {
    /**
     * 服务对象
     */
    @Resource
    private DeptManager deptManager;

    @ApiOperation(value = "部门列表",notes = "部门列表")
    @GetMapping("/list")
    public CommonRestResult list(){
        return RestBusinessTemplate.execute(()->deptManager.getDeptList());
    }
}
