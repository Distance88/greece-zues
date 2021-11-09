package com.zhang.common.base.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Yaohang Zhang
 * @ClassName TimeUtils
 * @description TODO
 * @date 2021-08-09 17:08
 */
@Component
public class TimeUtils {

    @Bean
    public String getCreateTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return simpleDateFormat.format(new Date());
    }

}