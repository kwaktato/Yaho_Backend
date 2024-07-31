package com.example.yaho.apiPayload.exception.handler;

import com.example.yaho.apiPayload.code.BaseErrorCode;
import com.example.yaho.apiPayload.exception.GeneralException;

public class GameIdHandler extends GeneralException {
    public GameIdHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }

}
