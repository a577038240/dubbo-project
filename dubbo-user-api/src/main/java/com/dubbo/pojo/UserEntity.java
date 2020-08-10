package com.dubbo.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ChenHao
 * @description TODO 写点注释吧
 * @date 2020-08-10 10:30
 * @Since V1.0.0
 */
@Data
public class UserEntity implements Serializable {

    private Long id;

    private String userId;

    private String userName;

    private String phoneNum;

    private Integer age;
}
