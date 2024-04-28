package com.newswhip.exception;

public class CommandInvalidException extends RuntimeException{
    public CommandInvalidException(String msg){
        super(msg);
    }
}
