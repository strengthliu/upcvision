package com.surpass.vision.exception;

public class ResponseBean<T> {

    private int code;

    private String message;

    private T data;

    public ResponseBean(){}

    public ResponseBean(ExceptionEnum exceptionEnum){
        this.code = exceptionEnum.getCode();
        this.message = exceptionEnum.getMessage();
    }

    // 省略 setter/getter
}