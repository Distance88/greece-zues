package com.zhang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Yaohang Zhang
 * @ClassName GreeceHadesApplication
 * @description TODO
 * @date 2021-10-11 15:24
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class GreeceHadesApplication {

    public static void main(String[] args) {
        SpringApplication.run(GreeceHadesApplication.class,args);
    }
}
