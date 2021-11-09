package com.zhang.project.biz.manager;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhang.common.core.exception.BizCoreException;
import com.zhang.project.dal.dao.ClassificationExercisesDAO;
import com.zhang.project.dal.dataobject.ClassificationExercises;
import com.zhang.project.web.form.ClassificationForm;
import com.zhang.project.web.vo.ClassificationVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 题目分类(ClassificationExercises)表服务实现类
 *
 * @author Distance
 * @since 2021-09-13 17:53:51
 */
@Service("classificationExercisesManager")
public class ClassificationExercisesManager {

    @Resource
    private ClassificationExercisesDAO classificationExercisesDAO;

    /**
     * 获取所有分类列表
     * @return
     */
    public List<ClassificationVO> getClassificationList(){

        QueryWrapper<ClassificationExercises> wrapper = new QueryWrapper<>();
        wrapper.eq("status",true);
        List<ClassificationExercises> classificationExercisesList = classificationExercisesDAO.selectList(wrapper);
        if(!classificationExercisesList.isEmpty()){
            return classificationExercisesList.stream().map(classificationExercises -> coverClassificationVO(classificationExercises))
                    .collect(Collectors.toList());
        }

        return null;
    }

    /**
     * 组装SERVICEClassificationVO
     * @param classificationExercises
     * @return
     */
    private ClassificationVO coverClassificationVO(ClassificationExercises classificationExercises){

        return ClassificationVO.builder()
                .classificationName(classificationExercises.getClassificationName())
                .openId(classificationExercises.getOpenId()).classificationCount(classificationExercises.getClassificationCount()).build();
    }

    /**
     * 新增分类学科
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public void createClassification(ClassificationForm form){
        ClassificationExercises classificationExercises = classificationExercisesDAO.findByClassificationTypeAndClassificationName(form.getClassificationType(), form.getClassificationName());
        if(ObjectUtil.isNotNull(classificationExercises)){
            throw new BizCoreException("该分类学科已存在!");
        }
        classificationExercises = new ClassificationExercises();
        classificationExercises.setOpenId(IdUtil.simpleUUID());
        classificationExercises.setClassificationName(form.getClassificationName());
        classificationExercises.setStatus(true);
        classificationExercises.setClassificationCount(0);
        int insert = classificationExercisesDAO.insert(classificationExercises);
        if(insert == 0){
            throw new BizCoreException("添加学科分类失败");
        }
    }

    /**
     * 查找所有分类名称
     * @return
     */
    public List<ClassificationVO> getClassificationExercisesName(){
        QueryWrapper<ClassificationExercises> wrapper = new QueryWrapper<>();
        wrapper.eq("status",1);
        List<ClassificationExercises> classificationExercisesList = classificationExercisesDAO.selectList(wrapper);
        if(!classificationExercisesList.isEmpty()){
            return classificationExercisesList.stream().map(classificationExercises -> coverClassificationVO2(classificationExercises))
                    .collect(Collectors.toList());
        }
        return null;
    }

    /**
     * 组装ADMINClassificationVO
     * @param classificationExercises
     * @return
     */
    private ClassificationVO coverClassificationVO2(ClassificationExercises classificationExercises){
        return ClassificationVO.builder()
                .openId(classificationExercises.getOpenId())
                .classificationName(classificationExercises.getClassificationName()).build();
    }
}
