package com.example.yaho.apiPayload.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import com.example.yaho.apiPayload.code.BaseErrorCode;
import com.example.yaho.apiPayload.code.ErrorReasonDTO;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {

    private BaseErrorCode code;

    public ErrorReasonDTO getErrorReason() {
        return this.code.getReason();
    }

    public ErrorReasonDTO getErrorReasonHttpStatus(){
        return this.code.getReasonHttpStatus();
    }
}
