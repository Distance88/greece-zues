package com.zhang.project.biz.manager;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhang.project.dal.dao.UserWrongExercisesDAO;
import com.zhang.project.dal.dataobject.UserWrongExercises;
import com.zhang.project.web.form.UserWrongExercisesForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 用户习题(UserWrongExercises)表服务实现类
 *
 * @author Distance
 * @since 2021-09-18 13:50:35
 */
@Service("userWrongExercisesManager")
public class UserWrongExercisesManager {

    @Resource
    private UserWrongExercisesDAO userWrongExercisesDAO;


    @Transactional(rollbackFor = Exception.class)
    public void create(UserWrongExercisesForm form){

        String userOpenId = form.getUserOpenId();
        QueryWrapper<UserWrongExercises> wrapper = new QueryWrapper<>();
        form.getWrongExercisesIdList().forEach(wrongExercisesId->{
            UserWrongExercises userWrongExercises = userWrongExercisesDAO.findByUserOpenIdAndExercisesId(userOpenId,wrongExercisesId);
            if(ObjectUtil.isNull(userWrongExercises)){
                userWrongExercises = new UserWrongExercises();
                userWrongExercises.setUserOpenId(userOpenId);
                userWrongExercises.setExercisesId(wrongExercisesId);
                userWrongExercises.setCount(1);
                userWrongExercisesDAO.insert(userWrongExercises);
            }else{
                userWrongExercises.setCount(userWrongExercises.getCount() + 1);
                userWrongExercisesDAO.updateById(userWrongExercises);
            }
        });
    }
}
