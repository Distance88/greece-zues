package com.zhang.project.biz.manager;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.common.core.exception.BizCoreException;
import com.zhang.integration.user.client.UserClient;
import com.zhang.project.biz.enums.ClassificationTypeEnum;
import com.zhang.project.dal.dao.UserMockExercisesDAO;
import com.zhang.project.dal.dataobject.UserMockExercises;
import com.zhang.project.web.form.UserMockExercisesForm;
import com.zhang.project.web.form.UserMockForm;
import com.zhang.project.web.vo.UserMockExercisesVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (UserMockExercises)表服务实现类
 *
 * @author Distance
 * @since 2021-09-18 13:52:10
 */
@Service("userMockExercisesManager")
public class UserMockExercisesManager {

    @Resource
    private UserMockExercisesDAO userMockExercisesDAO;

    @Resource
    private UserClient userClient;

    /**
     * 新增用户做题记录
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public void create(UserMockExercisesForm form){

        UserMockExercises userMockExercises = new UserMockExercises();
        userMockExercises.setOpenId(IdUtil.simpleUUID());
        userMockExercises.setTitle(form.getTitle());
        userMockExercises.setUserOpenId(form.getUserOpenId());
        userMockExercises.setExercisesRelationType(form.getExercisesRelationType());
        userMockExercises.setRelationId(form.getRelationId());
        userMockExercises.setMockDetail(form.getMockDetail());
        userMockExercises.setExercisesCount(form.getExercisesCount());
        userMockExercises.setExercisesPassCount(form.getExercisesPassCount());
        userMockExercises.setScores(form.getScores());
        userMockExercises.setTime(form.getTime());
        if(StringUtils.equals(ClassificationTypeEnum.PROJECT.getCode(),form.getExercisesRelationType())){
            userMockExercises.setStatus("已批阅");
        }else{
            userMockExercises.setStatus("未批阅");
        }
        userMockExercisesDAO.insert(userMockExercises);
    }

    /**
     * 用户题目记录列表
     * @param userOpenId
     * @param exercisesRelationType
     * @param current
     * @return
     */
    public Page<UserMockExercises> list(String userOpenId,String exercisesRelationType, Integer current){
        Page<UserMockExercises> page = new Page<>(current,12);
        QueryWrapper<UserMockExercises> wrapper = new QueryWrapper<>();
        wrapper.and(w->w.eq("user_open_id",userOpenId).eq("exercises_relation_type",exercisesRelationType));
        List<UserMockExercises> records = userMockExercisesDAO.selectPage(page, wrapper).getRecords();
        return (Page<UserMockExercises>) userMockExercisesDAO.selectPage(page,wrapper);
    }

    /**
     * 用户考试记录
     * @param form
     * @return
     */
    public Page<UserMockExercisesVO> list(UserMockForm form){

        Page<UserMockExercises> page = new Page<>(form.getCurrent(),form.getSize());
        QueryWrapper<UserMockExercises> wrapper = new QueryWrapper<>();
        wrapper.and(w->w.eq("exercises_relation_type", ClassificationTypeEnum.EXAM_QUESTIONS.getCode())
                .eq("relation_id",form.getRelationId()));
        List<UserMockExercises> records = userMockExercisesDAO.selectPage(page, wrapper).getRecords();
        if(records.isEmpty()){
            return null;
        }
        List<UserMockExercisesVO> userMockExercisesVOS = records.stream().map(userMockExercises -> coverUserMockExercisesVO(userMockExercises)).collect(Collectors.toList());
        Page<UserMockExercisesVO> resultPage = new Page<>(form.getCurrent(),form.getSize());
        resultPage.setRecords(userMockExercisesVOS);
        resultPage.setTotal(page.getTotal());
        return resultPage;
    }

    /**
     * 批阅试卷
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public void review(UserMockForm form){
        UserMockExercises userMockExercises = new UserMockExercises();
        UpdateWrapper<UserMockExercises> wrapper = new UpdateWrapper<>();
        wrapper.set("mock_detail",form.getMockDetail());
        wrapper.set("status",form.getStatus());
        wrapper.set("exercises_pass_count",form.getExercisesPassCount());
        wrapper.set("scores",form.getScores());
        wrapper.eq("open_id",form.getOpenId());
        userMockExercises.setStatus(form.getStatus());
        userMockExercises.setMockDetail(form.getMockDetail());
        userMockExercises.setExercisesPassCount(form.getExercisesPassCount());
        userMockExercises.setScores(form.getScores());
        userMockExercises.setOpenId(form.getOpenId());
        int update = userMockExercisesDAO.update(userMockExercises, wrapper);
        if(update == 0){
            throw new BizCoreException("批阅试卷失败");
        }

    }

    /**
     * 组装UserMockExercisesVO
     * @param userMockExercises
     * @return
     */
    private UserMockExercisesVO coverUserMockExercisesVO(UserMockExercises userMockExercises){
        String name = userClient.findNameByUserOpenId(userMockExercises.getUserOpenId());
        return UserMockExercisesVO.builder().openId(userMockExercises.getOpenId())
                .userOpenId(userMockExercises.getUserOpenId()).name(name)
                .exercisesCount(userMockExercises.getExercisesCount()).exercisesPassCount(userMockExercises.getExercisesPassCount())
                .scores(userMockExercises.getScores()).status(userMockExercises.getStatus())
                .mockDetail(userMockExercises.getMockDetail()).time(userMockExercises.getTime())
                .title(userMockExercises.getTitle()).createTime(userMockExercises.getCreateTime()).build();
    }

}
