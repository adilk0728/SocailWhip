package com.newswhip.exception;

public class CommandInvalidException extends RuntimeException{
    public CommandInvalidException(String err){
        super(err);
    }
}
