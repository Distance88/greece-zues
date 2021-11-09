package com.zhang.project.biz.manager;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.zhang.common.core.exception.BizCoreException;
import com.zhang.integration.user.client.UserClient;
import com.zhang.project.dal.dao.UserBlogLogRecordDAO;
import com.zhang.project.dal.dataobject.Blog;
import com.zhang.project.dal.dataobject.UserBlogLogRecord;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * (UserBlogLogRecord)表服务实现类
 *
 * @author Distance
 * @since 2021-10-15 13:56:45
 */
@Service("userBlogLogRecordManager")
public class UserBlogLogRecordManager {

    @Resource
    private UserBlogLogRecordDAO userBlogLogRecordDAO;

    @Resource
    private UserClient userClient;

    @Transactional(rollbackFor = Exception.class)
    public void createUserBlogLogRecord(Blog blog,String operationType){
        String name = userClient.findNameByUserOpenId(blog.getUserOpenId());
        if(StringUtils.isBlank(name)){
            throw new BizCoreException("没有找到此用户");
        }
        UserBlogLogRecord userBlogLogRecord = new UserBlogLogRecord();
        userBlogLogRecord.setOpenId(IdUtil.fastSimpleUUID());
        userBlogLogRecord.setUserOpenId(blog.getUserOpenId());
        userBlogLogRecord.setBlogOpenId(blog.getOpenId());
        userBlogLogRecord.setDetail(JSON.toJSONString(blog));
        String operation = StringUtils.join(name,operationType,"了一篇博客：",blog.getTitle());
        userBlogLogRecord.setOperation(operation);
        userBlogLogRecordDAO.insert(userBlogLogRecord);
    }


}
