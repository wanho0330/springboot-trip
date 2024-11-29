package com.wanho.trip.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
public class APIResponse<T> {

    private final Result result;
    private final T data;
    private final T error;

    public APIResponse(Result result, T data, T error) {
        this.result = result;
        this.data = data;
        this.error = error;
    }

    public static <T> APIResponse<T> success(T data) {
        return new APIResponse<>(Result.success(), data, null);
    }

    public static APIResponse<?> failed(HttpStatus httpStatus, Map<String, String> errors) {
        return new APIResponse<>(Result.failed(httpStatus), null, errors);
    }
}
