package com.dubbo.api;

import com.dubbo.pojo.UserEntity;

/**
 * @author ChenHao
 * @description TODO 写点注释吧
 * @date 2020-08-10 10:32
 * @Since V1.0.0
 */
public interface UserService {


    Long registerUser(UserEntity userEntity) throws InterruptedException;


    UserEntity getUserById(Long id);
}
