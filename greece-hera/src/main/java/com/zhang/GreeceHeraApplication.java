package com.zhang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Yaohang Zhang
 * @ClassName GreeceHeraApplication
 * @description TODO
 * @date 2021-10-09 14:17
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GreeceHeraApplication {

    public static void main(String[] args) {
        SpringApplication.run(GreeceHeraApplication.class,args);
    }
}
