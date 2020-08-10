package com.dubbo.consumer;

import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ChenHao
 * @description TODO 写点注释吧
 * @date 2020-08-10 11:03
 * @Since V1.0.0
 */
@SpringBootApplication
public class UserConsumerApp {
    public static void main(String[] args) {
        SpringApplication.run(UserConsumerApp.class, args);
    }
}
