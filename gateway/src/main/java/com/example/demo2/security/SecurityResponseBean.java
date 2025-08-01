package com.example.demo2.security;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class SecurityResponseBean<T> {

    private static final Integer SUCCESS_CODE = 200;
    private int code; //成功 200  失败 401
    private String msg;
    private T data;

    public SecurityResponseBean(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> SecurityResponseBean<T> sysTermErrorFactory(String mag) {
        return new SecurityResponseBean<>(401, mag, null);
    }

    public static <T> SecurityResponseBean<T> loginOutFactory(String mag) {
        return new SecurityResponseBean<>(200, mag, null);
    }

    public static <T> SecurityResponseBean<T> accessDeniedFactory(String mag) {
        return new SecurityResponseBean<>(200, mag, null);
    }

}
