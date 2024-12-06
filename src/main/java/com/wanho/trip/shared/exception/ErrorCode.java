package com.wanho.trip.shared.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    DUPLICATE_EMAIL("USER001", "The email address is already in use."),
    INVALID_PASSWORD("USER002", "The password provided is invalid."),
    USER_NOT_FOUND("USER003", "User not found.");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
