package com.dubbo.consumer.controller;

import com.alibaba.fastjson.JSON;
import com.dubbo.api.UserService;
import com.dubbo.pojo.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * @author ChenHao
 * @description TODO 写点注释吧
 * @date 2020-08-10 11:05
 * @Since V1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * parameters = {"registerUser.retries","0","registerUser.cluster","failfast","registerUser.timeout","10000"}
     * 针对方法级别做超时时间和容错等设置
     */

    @DubboReference(timeout = 1000, retries = 2,filter = "traceLog",cluster = "failover",parameters = {"registerUser.retries","0","registerUser.cluster","failfast","registerUser.timeout","10000"})
    private UserService userService;

    @PostMapping("/register")
    public Long register(@RequestBody UserEntity userEntity) throws InterruptedException {
        log.info("注册用户信息: {}", JSON.toJSONString(userEntity));
        Long id = userService.registerUser(userEntity);
        return id;
    }

    @GetMapping("get/{id}")
    public UserEntity getUserById(@PathVariable("id")Long id){
        log.info("查询用户，id = {}",id);
        UserEntity user = userService.getUserById(id);
        log.info("id = {},用户信息: {}",id,JSON.toJSONString(user));
        return user;
    }
}
