package com.dubbo.provider.service.impl;

import com.alibaba.fastjson.JSON;
import com.dubbo.api.UserService;
import com.dubbo.pojo.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author ChenHao
 * @description TODO 写点注释吧
 * @date 2020-08-10 10:51
 * @Since V1.0.0
 */
@DubboService(retries = 2, timeout = 1000)
@Slf4j
public class UserServiceImpl implements UserService {


    private static Map<Long, UserEntity> USER_CACHE = new ConcurrentHashMap<>();


    private static AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Long registerUser(UserEntity userEntity) throws InterruptedException {
        Long id = idGenerator.getAndIncrement();

        Thread.sleep(2000);
        log.info("注册用户，id = {}，user = {}",id,JSON.toJSONString(userEntity));
        USER_CACHE.put(id,userEntity);
        log.info("注册用户成功！id = {}",id);
        return id;
    }

    @Override
    public UserEntity getUserById(Long id) {
        log.info("查询用户信息start, id = {}",id);
        UserEntity userEntity = USER_CACHE.get(id);
        log.info("查询id = {}的用户信息 ：{}",id,JSON.toJSONString(userEntity));
        return userEntity;
    }
}
