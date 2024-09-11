package com.demo1.blogspringboot.exception;

import lombok.Getter;

/**
 * 功能:
 * 作者: wilhelmaoi
 * 目期: 2024年9月10日 08:09
 */
@Getter
public class ServiceException extends RuntimeException {
    private final String code;

    public ServiceException(String msg) {
        super(msg);
        this.code = "500";
    }

    public ServiceException(String code, String msg) {
        super(msg);
        this.code = code;
    }
}