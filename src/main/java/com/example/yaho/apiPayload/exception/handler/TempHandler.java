package com.example.yaho.apiPayload.exception.handler;

import com.example.yaho.apiPayload.code.BaseErrorCode;
import com.example.yaho.apiPayload.exception.GeneralException;

public class TempHandler extends GeneralException {

    public TempHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
