package com.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.dubbo.api.UserService;
import com.dubbo.api.order.OrderService;
import com.dubbo.pojo.UserEntity;
import com.dubbo.to.OrderCreateTO;
import com.order.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author ChenHao
 * @description TODO 写点注释吧
 * @date 2020-08-13 11:18
 * @Since V1.0.0
 */
@DubboService
@Slf4j
public class OrderServiceImpl implements OrderService {


    @DubboReference
    private UserService userService;

    @Override
    public int createOrder(OrderCreateTO orderCreateTO) {
        log.info("create order start this param = {}", JSON.toJSONString(orderCreateTO));
        if(StringUtils.isBlank(orderCreateTO.getOrderId())){
            throw  new ServiceException("400","订单id为空");
        }
        Long userId = orderCreateTO.getBuyerId();
        UserEntity user = userService.getUserById(userId);
        log.info("userId = {} userId = {}",userId,JSON.toJSONString(user));
        return 1;
    }
}
