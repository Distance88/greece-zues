package com.zhang.project.biz.manager;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.common.core.exception.BizCoreException;
import com.zhang.project.biz.enums.ClassificationTypeEnum;
import com.zhang.project.biz.enums.ExerciseTypeEnum;
import com.zhang.project.dal.dao.ExamDAO;
import com.zhang.project.dal.dao.ExercisesDAO;
import com.zhang.project.dal.dao.UserMockExercisesDAO;
import com.zhang.project.dal.dataobject.Exam;
import com.zhang.project.dal.dataobject.Exercises;
import com.zhang.project.dal.dataobject.UserMockExercises;
import com.zhang.project.web.form.ExamForm;
import com.zhang.project.web.vo.ExamDetailVO;
import com.zhang.project.web.vo.ExamVO;
import com.zhang.project.web.vo.ExercisesVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 试卷表(Exam)表服务实现类
 *
 * @author Distance
 * @since 2021-09-26 17:14:16
 */
@Service("examManager")
public class ExamManager {

    @Resource
    private ExamDAO examDAO;

    @Resource
    private ExercisesDAO exercisesDAO;

    @Resource
    private UserMockExercisesDAO userMockExercisesDAO;

    /**
     * 获取考试真题信息列表
     * @param current
     * @return
     */
    public Page<ExamVO> infoList(Integer current) {

        Page<Exam> page = new Page<>(current, 12);
        QueryWrapper<Exam> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1);
        List<Exam> records = examDAO.selectPage(page, wrapper).getRecords();
        if (records.isEmpty()) {
            return null;
        }
        List<ExamVO> examVOList = records.stream().map(exam -> coverExamVO(exam)).collect(Collectors.toList());
        Page<ExamVO> resultPage = new Page<>(current, 12);
        resultPage.setRecords(examVOList);
        return resultPage;
    }

    /**
     * 考试信息详情
     * @param openId
     * @param userOpenId
     * @return
     */
    public ExamDetailVO examDetail(String openId, String userOpenId) {
        List<UserMockExercises> userMockExercises = userMockExercisesDAO.findByUserOpenIdAndExamOpenId(userOpenId,openId);
        if(!userMockExercises.isEmpty()){
            throw new BizCoreException("您已经参加本次考试，不准重复考试");
        }
        QueryWrapper<Exercises> wrapper = new QueryWrapper<>();
        wrapper.and(w -> w.eq("relation_id", openId).eq("status", 1).eq("exercises_relation_type", ClassificationTypeEnum.EXAM_QUESTIONS));
        List<Exercises> exercisesList = exercisesDAO.selectList(wrapper);
        if(exercisesList.isEmpty()){
            return null;
        }
        List<ExercisesVO> choiceQuestion = new ArrayList<>();
        List<ExercisesVO> fillQuestion = new ArrayList<>();
        List<ExercisesVO> operationQuestion = new ArrayList<>();
        exercisesList.forEach(exercises -> {
            if (StringUtils.equals(ExerciseTypeEnum.CHOICE_QUESTION.getCode(), exercises.getExerciseType())) {
                choiceQuestion.add(ExercisesVO.builder()
                        .exerciseType(exercises.getExerciseType())
                        .content(exercises.getContent()).optionA(exercises.getOptionA())
                        .optionB(exercises.getOptionB()).optionC(exercises.getOptionC())
                        .optionD(exercises.getOptionD()).score(exercises.getScore())
                        .answer(exercises.getAnswer()).analysis(exercises.getAnalysis()).build());
                fillQuestion.add(null);
                operationQuestion.add(null);
            }
            if(StringUtils.equals(ExerciseTypeEnum.FILL_QUESTION.getCode(), exercises.getExerciseType())){
                fillQuestion.add(ExercisesVO.builder()
                        .exerciseType(exercises.getExerciseType()).answer(exercises.getAnswer())
                        .content(exercises.getContent()).score(exercises.getScore()).analysis(exercises.getAnalysis()).build());
                operationQuestion.add(null);
            }
            if(StringUtils.equals(ExerciseTypeEnum.OPERATION_QUESTION.getCode(), exercises.getExerciseType())){
                operationQuestion.add(ExercisesVO.builder()
                        .exerciseType(exercises.getExerciseType()).analysis(exercises.getAnalysis())
                        .content(exercises.getContent()).score(exercises.getScore()).answer(exercises.getAnswer()).build());
            }
        });
        return ExamDetailVO.builder()
                .choiceQuestion(choiceQuestion).fillQuestion(fillQuestion)
                .operationQuestion(operationQuestion).build();
    }

    /**
     * 组装SERVICEExamVO
     * @param exam
     * @return
     */
    private ExamVO coverExamVO(Exam exam) {
        String examStatus = exam.getExamStatus();
        if (System.currentTimeMillis() < DateUtil.parse(exam.getExamBegin()).getTime()) {
            examStatus = "未开始";
        } else if (System.currentTimeMillis() <= DateUtil.parse(exam.getExamEnd()).getTime()) {
            examStatus = "考试中";
        } else {
            examStatus = "已结束";
        }
        if (!StringUtils.equals(examStatus, exam.getExamStatus())) {
            exam.setExamStatus(examStatus);
            examDAO.updateById(exam);
        }
        return ExamVO.builder()
                .openId(exam.getOpenId()).title(exam.getTitle())
                .questionCount(exam.getQuestionCount()).examScore(exam.getExamScore())
                .examBegin(exam.getExamBegin()).examEnd(exam.getExamEnd())
                .examStatus(examStatus).build();
    }

    /**
     * 新增考试
     * @param examForm
     */
    @Transactional(rollbackFor = Exception.class)
    public void createExam(ExamForm examForm){

        String openId = IdUtil.simpleUUID();
        Exam exam = new Exam();
        exam.setOpenId(openId);
        exam.setRelationId(examForm.getRelationId());
        exam.setTitle(examForm.getTitle());
        exam.setQuestionCount(examForm.getQuestionCount());
        exam.setExamScore(examForm.getExamScore());
        exam.setExamBegin(DateUtil.formatDateTime(examForm.getExamBegin()));
        exam.setExamEnd(DateUtil.formatDateTime(examForm.getExamEnd()));
        exam.setExamStatus("未开始");
        exam.setStatus(true);
        examDAO.insert(exam);

        for(ExerciseTypeEnum exerciseType:ExerciseTypeEnum.values()){
            Integer examCount = 0;
            Integer examScore = 0;
            if(ObjectUtil.equal(ExerciseTypeEnum.CHOICE_QUESTION,exerciseType)){
                examCount = examForm.getChoiceQuestion().getCount();
                examScore = examForm.getChoiceQuestion().getScore();
            }else if(ObjectUtil.equal(ExerciseTypeEnum.FILL_QUESTION,exerciseType)){
                examCount = examForm.getFillQuestion().getCount();
                examScore = examForm.getFillQuestion().getScore();
            }else{
                examCount = examForm.getOperationQuestion().getCount();
                examScore = examForm.getOperationQuestion().getScore();
            }
            List<Exercises> exercisesList = exercisesDAO.findByExerciseTypeRandom(examForm.getRelationId(),exerciseType.getCode(), examCount);
            if(exercisesList.isEmpty()){
                continue;
            }
            for(Exercises exercises:exercisesList){
                exercises.setId(null);
                exercises.setOpenId(IdUtil.simpleUUID());
                exercises.setRelationId(openId);
                exercises.setExercisesRelationType(ClassificationTypeEnum.EXAM_QUESTIONS.getCode());
                exercises.setScore(examScore);
                exercisesDAO.insert(exercises);
            }
        }
    }

    /**
     * 获取考试信息列表
     * @param form
     * @return
     */
    public Page<ExamVO> getList(ExamForm form){

        Page<Exam> page = new Page<>(form.getCurrent(),form.getSize());
        QueryWrapper<Exam> wrapper = new QueryWrapper<>();
        Map<String,Object> map = new HashMap<>();
        if(StringUtils.isNotBlank(form.getTitle())){
            map.put("title",form.getTitle());
        }
        if(ObjectUtil.isNotNull(form.getStatus())){
            map.put("status",form.getStatus());
        }
        if(ObjectUtil.isNotNull(form.getExamStatus())){
            map.put("exam_status",form.getStatus());
        }
        wrapper.allEq(map);
        List<Exam> records = examDAO.selectPage(page, wrapper).getRecords();
        if(records.isEmpty()){
            return null;
        }
        List<ExamVO> examVOList = records.stream().map(exam -> coverExamVO2(exam)).collect(Collectors.toList());
        Page<ExamVO> resultPage = new Page<>(form.getCurrent(),form.getSize());
        resultPage.setRecords(examVOList).setTotal(page.getTotal());
        return resultPage;
    }

    /**
     * 组装ADMINExamVO2
     * @param exam
     * @return
     */
    private ExamVO coverExamVO2(Exam exam){
        return ExamVO.builder()
                .openId(exam.getOpenId()).title(exam.getTitle())
                .examBegin(exam.getExamBegin()).examEnd(exam.getExamEnd())
                .questionCount(exam.getQuestionCount()).examScore(exam.getExamScore())
                .examStatus(exam.getExamStatus()).status(exam.getStatus()).build();
    }
}
