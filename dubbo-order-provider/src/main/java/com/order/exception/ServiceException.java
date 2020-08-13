package com.order.exception;

/**
 * @author ChenHao
 * @description TODO 写点注释吧
 * @date 2020-08-13 11:46
 * @Since V1.0.0
 */
public class ServiceException extends RuntimeException {

    private String errorCode;

    private String msg;

    public ServiceException(){

    }
    public ServiceException(String errorCode,String msg){
        super(msg);
        this.errorCode=errorCode;
    }
}
