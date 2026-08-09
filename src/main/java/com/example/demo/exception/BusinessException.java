package com.example.demo.exception;

/**
 * 自定义业务异常
 * 用于在业务逻辑中主动抛出异常，例如：书籍不存在、库存不足等
 */
public class BusinessException extends RuntimeException {

    private int code;

    public BusinessException(String message) {
        super(message);
        this.code = 500;
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
