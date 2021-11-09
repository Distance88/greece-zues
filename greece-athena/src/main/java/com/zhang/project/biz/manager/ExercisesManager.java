package com.zhang.project.biz.manager;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.common.core.exception.BizCoreException;
import com.zhang.project.biz.enums.ExerciseTypeEnum;
import com.zhang.project.biz.enums.ExercisesOriginTypeEnum;
import com.zhang.project.dal.dao.ClassificationExercisesDAO;
import com.zhang.project.dal.dao.ExercisesDAO;
import com.zhang.project.dal.dao.UserWrongExercisesDAO;
import com.zhang.project.dal.dataobject.Exercises;
import com.zhang.project.web.form.ExercisesForm;
import com.zhang.project.web.vo.ExercisesVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 题目(Exercises)表服务实现类
 *
 * @author Distance
 * @since 2021-09-14 16:18:19
 */
@Service("exercisesManager")
public class ExercisesManager {

    @Resource
    private ExercisesDAO exercisesDAO;

    @Resource
    private ClassificationExercisesDAO classificationExercisesDAO;

    @Resource
    private UserWrongExercisesDAO userWrongExercisesDAO;

    /**
     * 专项练习获取题目信息
     * @param form
     * @return
     */
    @SuppressWarnings("all")
    @Transactional(rollbackFor = Exception.class)
    public List<ExercisesVO> getExercisesByProject(ExercisesForm form){

        List<Integer> ids = new ArrayList<>();
        for(int i=0;i<form.getCount();i++){
            ids.add(new Random().nextInt(form.getClassificationCount())+1);
        }
        QueryWrapper<Exercises>wrapper = new QueryWrapper<>();
        wrapper.and(w->
                w.eq("exercise_type","CHOICE_QUESTION").eq("exercises_relation_type","PROJECT")
                        .eq("relation_id",form.getRelationId()).eq("status",true)
                        .in("id",ids));
        List<Exercises> exercisesList = exercisesDAO.selectList(wrapper);
        if(StringUtils.equals(form.getOrigin(), ExercisesOriginTypeEnum.NEW.getCode())){
            List<String> wrongExercisesId = userWrongExercisesDAO.findExercisesIdByUserOpenId(form.getUserOpenId());
            exercisesList = exercisesList.stream()
                    .filter(exercises -> !wrongExercisesId.contains(exercises.getOpenId())).collect(Collectors.toList());
        }
        if(StringUtils.equals(form.getOrigin(), ExercisesOriginTypeEnum.WRONG.getCode())){
            List<String> wrongExercisesId = userWrongExercisesDAO.findExercisesIdByUserOpenId(form.getUserOpenId());
            exercisesList = exercisesList.stream()
                    .filter(exercises -> wrongExercisesId.contains(exercises.getOpenId())).collect(Collectors.toList());
        }
        if(exercisesList.isEmpty()){
            return null;
        }
        return exercisesList.stream().map(exercises -> this.coverExercisesVO(exercises)).collect(Collectors.toList());
    }

    /**
     * 组装SERVICEExercisesVO
     * @param exercises
     * @return
     */
    private ExercisesVO coverExercisesVO(Exercises exercises){

        String classificationName = classificationExercisesDAO.findClassificationNameByOpenId(exercises.getRelationId());

        return ExercisesVO.builder()
                .openId(exercises.getOpenId()).answer(exercises.getAnswer()).classificationName(classificationName)
                .content(exercises.getContent()).relationId(exercises.getRelationId()).analysis(exercises.getAnalysis())
                .optionA(exercises.getOptionA()).optionB(exercises.getOptionB()).optionC(exercises.getOptionC())
                .optionD(exercises.getOptionD()).build();
    }

    /**
     * 获取题目列表
     * @param form
     * @return
     */
    @SuppressWarnings("all")
    public Page<ExercisesVO> getExercisesList(ExercisesForm form){

        Page<Exercises> page = new Page<>(form.getCurrent(),form.getSize());
        QueryWrapper<Exercises> wrapper = new QueryWrapper<>();
        Map<String,Object> map = new HashMap<>();
        if(StringUtils.isNotBlank(form.getExerciseType())){
            map.put("exercise_type",form.getExerciseType());
        }
        if(StringUtils.isNotBlank(form.getRelationId())){
            map.put("relation_id",form.getRelationId());
        }
        wrapper.allEq(map);
        List<Exercises> records = exercisesDAO.selectPage(page, wrapper).getRecords();
        if(records.isEmpty()){
            return null;
        }
        List<ExercisesVO> exercisesVOList = records.stream().map(exercises -> coverExercisesVO2(exercises)).collect(Collectors.toList());
        Page<ExercisesVO> resultPage = new Page<>(form.getCurrent(),form.getSize());
        resultPage.setRecords(exercisesVOList).setTotal(page.getTotal());
        return resultPage;
    }

    /**
     * 添加题目
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public void createExercises(ExercisesForm form) {
        Exercises exercises = new Exercises();
        exercises.setOpenId(IdUtil.simpleUUID());
        exercises.setExerciseType(form.getExerciseType());
        exercises.setExercisesRelationType(form.getExercisesRelationType());
        exercises.setRelationId(form.getRelationId());
        exercises.setContent(form.getContent());
        if (StringUtils.equals(ExerciseTypeEnum.CHOICE_QUESTION.getCode(), form.getExerciseType())) {
            exercises.setOptionA(form.getOptionA());
            exercises.setOptionB(form.getOptionB());
            exercises.setOptionC(form.getOptionC());
            exercises.setOptionD(form.getOptionD());
        }
        exercises.setAnswer(form.getAnswer());
        exercises.setAnalysis(form.getAnalysis());
        exercises.setScore(form.getScore());
        exercises.setLevel(form.getLevel());
        exercisesDAO.insert(exercises);
        int updateCount = classificationExercisesDAO.increaseClassificationCountByOpenId(form.getRelationId());
        if(updateCount == 0){
            throw new BizCoreException("题目数量增加失败");
        }
    }

    /**
     * 修改题目
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(ExercisesForm form){

        UpdateWrapper<Exercises> wrapper = new UpdateWrapper<>();
        wrapper.set(StringUtils.isNotBlank(form.getContent()),"content",form.getContent())
                .set(StringUtils.isNotBlank(form.getOptionA()),"option_a",form.getOptionA())
                .set(StringUtils.isNotBlank(form.getOptionB()),"option_b",form.getOptionB())
                .set(StringUtils.isNotBlank(form.getOptionC()),"option_c",form.getOptionC())
                .set(StringUtils.isNotBlank(form.getOptionD()),"option_d",form.getOptionD())
                .set(StringUtils.isNotBlank(form.getAnalysis()),"answer",form.getAnswer())
                .set(StringUtils.isNotBlank(form.getAnalysis()),"analysis",form.getAnalysis())
                .eq("id",form.getId());
        Exercises exercises = new Exercises();
        exercises.setContent(form.getContent());
        if (StringUtils.equals(ExerciseTypeEnum.CHOICE_QUESTION.getCode(), form.getExerciseType())) {
            exercises.setOptionA(form.getOptionA());
            exercises.setOptionB(form.getOptionB());
            exercises.setOptionC(form.getOptionC());
            exercises.setOptionD(form.getOptionD());
        }
        exercises.setAnswer(form.getAnswer());
        exercises.setAnalysis(form.getAnalysis());
        int update = exercisesDAO.update(exercises, wrapper);
        if(update == 0){
            throw new BizCoreException("题目修改失败");
        }
    }

    /**
     * 修改题目状态
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Integer id){
        int updateStatusById = exercisesDAO.updateStatusById(id);
        if(updateStatusById == 0){
            throw new BizCoreException("修改状态失败");
        }
    }

    /**
     * 组装题目VO
     * @param exercises
     * @return
     */
    private ExercisesVO coverExercisesVO2(Exercises exercises){
        String classificationName = classificationExercisesDAO.findClassificationNameByRelationId(exercises.getRelationId());
        return ExercisesVO.builder()
                .id(exercises.getId()).exerciseType(exercises.getExerciseType()).answer(exercises.getAnswer())
                .content(exercises.getContent()).relationId(exercises.getRelationId()).analysis(exercises.getAnalysis())
                .status(exercises.getStatus()).score(exercises.getScore()).level(exercises.getLevel()).optionA(exercises.getOptionA())
                .optionB(exercises.getOptionB()).optionC(exercises.getOptionC()).optionD(exercises.getOptionD())
                .classificationName(classificationName).build();
    }
}
