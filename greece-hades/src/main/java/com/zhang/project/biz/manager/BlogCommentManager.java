package com.zhang.project.biz.manager;

import com.zhang.project.dal.dao.BlogCommentDAO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (BlogComment)表服务实现类
 *
 * @author Distance
 * @since 2021-10-11 10:42:03
 */
@Service("blogCommentManager")
public class BlogCommentManager {

    @Resource
    private BlogCommentDAO blogCommentDAO;


}
