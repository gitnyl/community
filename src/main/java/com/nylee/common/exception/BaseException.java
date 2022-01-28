package com.nylee.common.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private String errMsg;
    private Exception exception;

    public BaseException(String errMsg , Exception e) {
        this.errMsg = errMsg;
        this.exception = e;
    }

    public BaseException(String errMsg) {
        this.errMsg = errMsg;
    }

}
