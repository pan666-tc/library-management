package com.example.demo.exception;

import com.example.demo.Result.result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 全局异常处理器
 * @RestControllerAdvice 会拦截所有Controller抛出的异常，统一处理后返回
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理参数校验异常（@Valid校验失败时触发）
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public result<Void> handleValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        String message = fieldErrors.get(0).getDefaultMessage();
        return result.error(400, message);
    }

    /**
     * 处理自定义业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public result<Void> handleBusinessException(BusinessException e) {
        return result.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理数据库异常
     */
    @ExceptionHandler(Exception.class)
    public result<Void> handleException(Exception e) {
        log.error("系统异常", e);
        return result.error("服务器内部错误，请联系管理员");
    }
}
