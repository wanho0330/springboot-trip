package com.wanho.trip.common.exception;


import lombok.Getter;

@Getter
public class InvalidInputException extends RuntimeException {
    private final String filedName;
    private final String message;

    public InvalidInputException(String filedName, String message) {
        super(message);
        this.filedName = filedName;
        this.message = message;
    }
}
