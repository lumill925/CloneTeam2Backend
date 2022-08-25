package com.sparta.cloneteam2backend.error.exception;

import com.sparta.cloneteam2backend.error.ErrorCode;

/* 유효하지 않은 값일 경우 예외를 던지는 Exception*/
public class InvalidValueException extends BusinessException{
    public InvalidValueException(ErrorCode errorCode) {
        super(errorCode);
    }
}
