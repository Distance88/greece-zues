package com.zhang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Yaohang Zhang
 * @ClassName GreeceGateWayApplication
 * @description TODO
 * @date 2021-10-09 15:38
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GreeceGateWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GreeceGateWayApplication.class,args);
    }
}
