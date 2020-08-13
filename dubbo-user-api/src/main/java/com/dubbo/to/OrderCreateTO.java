package com.dubbo.to;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ChenHao
 * @description TODO 写点注释吧
 * @date 2020-08-13 9:29
 * @Since V1.0.0
 */
@Data
public class OrderCreateTO implements Serializable {

    String orderId;

    private int totalFee;

    private Long buyerId;

    private int payType;

    private String productName;

    private String productId;


}
