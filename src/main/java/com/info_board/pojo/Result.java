package com.info_board.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class Result<T> {
    private Integer code;// 0-success  1-fail
    private String message;
    private T data;


    public static <T> Result<T> success(T data) {
        return new Result<>(0, "OK", data);
    }


    public static <T> Result<T> success() {
        return new Result<>(0, "OK", null);
    }

    public static <T> Result<T> error(String message) {
        return new Result<>(1, message, null);
    }
}
