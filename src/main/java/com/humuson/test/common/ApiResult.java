package com.humuson.test.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResult<T> {
    private int status;
    private T data;
    private String message;

    public static <T> ApiResult<T> success(T data, HttpStatus status) {
        return (ApiResult<T>) ApiResult.builder()
                .data(data)
                .status(status.value())
                .build();
    }

    public static <T> ApiResult<T> fail(HttpStatus status, String message) {
        return (ApiResult<T>) ApiResult.builder()
                .status(status.value())
                .message(message)
                .build();
    }

}
