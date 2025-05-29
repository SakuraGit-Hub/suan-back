package com.kousuan.common.exception;

import com.kousuan.common.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author ljx
 * 全局异常处理器
 * 捕获并处理控制器抛出的各种异常
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理所有未知异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<String>> handleException(Exception e) {
        // 记录错误日志
        logger.error("系统异常: ", e);

        // 返回统一错误响应
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Result.serverError("系统繁忙，请稍后再试"));
    }

    /**
     * 处理参数校验异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Result<String>> handleIllegalArgumentException(IllegalArgumentException e) {
        // 记录警告日志
        logger.warn("参数校验异常: {}", e.getMessage());

        // 返回参数错误响应
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Result.paramError(e.getMessage()));
    }
}