package com.example.yaho.apiPayload.code;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
@AllArgsConstructor
public class ErrorReasonDTO {

    private final HttpStatus httpStatus;

    private final Boolean isSuccess;
    private final String code;
    private final String message;


}
