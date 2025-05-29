package com.kousuan.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @author ljx
 * 统一API响应封装类
 * 包含状态码、消息和返回数据
 * @param <T> 数据类型
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> {
    // Getter方法
    private int code;       // 状态码
    private String msg;    // 消息提示
    private T data;       // 返回数据

    /**
     * 私有构造方法
     */
    private Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 成功响应（不带数据）
     */
    public static <T> Result<T> success() {
        return success(null);
    }

    /**
     * 成功响应（带数据）
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(HttpStatus.OK.value(), "操作成功", data);
    }

    /**
     * 失败响应
     */
    public static <T> Result<T> fail(int code, String msg) {
        return new Result<>(code, msg, null);
    }

    /**
     * 参数错误响应
     */
    public static <T> Result<T> paramError(String msg) {
        return fail(HttpStatus.BAD_REQUEST.value(), msg);
    }

    /**
     * 服务器错误响应
     */
    public static <T> Result<T> serverError(String msg) {
        return fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
    }

}