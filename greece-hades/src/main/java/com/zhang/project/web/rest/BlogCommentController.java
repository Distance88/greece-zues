package com.zhang.project.web.rest;

import com.zhang.project.biz.manager.BlogCommentManager;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (BlogComment)表控制层
 *
 * @author Distance
 * @since 2021-10-11 10:42:02
 */
@RestController
@RequestMapping("blogComment")
public class BlogCommentController {
    /**
     * 服务对象
     */
    @Resource
    private BlogCommentManager blogCommentManager;


}
