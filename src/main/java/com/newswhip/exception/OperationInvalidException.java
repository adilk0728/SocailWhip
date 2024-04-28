package com.newswhip.exception;

public class OperationInvalidException extends RuntimeException {
    public OperationInvalidException(String msg){
        super(msg);
    }
}
