package com.wanho.trip.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class Result {
    private int successCode;
    private String resultCode;
    private String resultMsg;

    public Result(int successCode, String resultCode, String resultMsg) {
        this.successCode = successCode;
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    public static Result success() {
        return new Result(1, "success", "success");
    }

    public static Result failed(HttpStatus httpStatus) {
        return new Result(0, httpStatus.value() + " " + httpStatus.name(), "failed");
    }

}
