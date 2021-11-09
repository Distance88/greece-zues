package com.zhang.common.base.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created with IntelliJ IDEA.
 * Author: Distance
 * Date: 2021/03/21/10:34
 */
@MapperScan("com.zhang.project.dal.dao")
@EnableTransactionManagement
@Configuration
public class MybatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {

        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();

        return paginationInterceptor;
    }
}
