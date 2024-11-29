package com.wanho.trip.common.status;

import lombok.Getter;

@Getter
public enum ResultCode {
    SUCCESS(1, "success"),
    ERROR(0, "error");

    private final int result;
    private final String msg;

    ResultCode(int result, String msg) {
        this.result = result;
        this.msg = msg;
    }
}
