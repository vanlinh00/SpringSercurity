package com.example.SpringSecurity.dto.response;

import com.example.SpringSecurity.dto.ResponseData;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ResponseUtil {

    public static <T> ResponseData<T> ok(T data, String message) {
        ResponseData<T> response = new ResponseData<>(message,data);
        response.setStatus(HttpStatus.OK.name());
        response.setCode(HttpStatus.OK.value());
        response.setAction(List.of("VIEW", "EDIT"));
        return response;
    }
}
