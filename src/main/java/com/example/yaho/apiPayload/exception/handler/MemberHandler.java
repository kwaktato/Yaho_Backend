package com.example.yaho.apiPayload.exception.handler;

import com.example.yaho.apiPayload.code.BaseErrorCode;
import com.example.yaho.apiPayload.exception.GeneralException;

public class MemberHandler extends GeneralException {

    public MemberHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
