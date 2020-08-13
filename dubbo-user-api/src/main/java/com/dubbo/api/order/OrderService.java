package com.dubbo.api.order;

import com.dubbo.to.OrderCreateTO;

/**
 * @author ChenHao
 * @description TODO 写点注释吧
 * @date 2020-08-13 9:28
 * @Since V1.0.0
 */
public interface OrderService {

    int createOrder(OrderCreateTO orderCreateTO);
}
