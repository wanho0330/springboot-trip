package com.wanho.trip.common.exception;


import lombok.Getter;

@Getter
public class InvalidInputException extends RuntimeException {
    private final String filedName;
    private final ErrorCode errorCode;

    public InvalidInputException(String filedName, ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.filedName = filedName;
        this.errorCode = errorCode;
    }
}
