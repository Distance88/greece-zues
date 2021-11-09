package com.zhang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Yaohang Zhang
 * @ClassName GreeceAyhenaApplication
 * @description TODO
 * @date 2021-10-10 14:33
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class GreeceAthenaApplication {

    public static void main(String[] args) {
        SpringApplication.run(GreeceAthenaApplication.class,args);
    }
}
