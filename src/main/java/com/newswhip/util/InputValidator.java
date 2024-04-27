package com.newswhip.util;

import com.newswhip.exception.CommandInvalidException;
import com.newswhip.exception.OperationInvalidException;
import com.newswhip.model.Operation;

public final class InputValidator {
    private InputValidator(){}

        public static <T> void checkNotNull(T input){
            if(input == null) {
                throw new NullPointerException("Input cannot be Empty");
            }
        }

        public static void runValidation(String[] command){
            checkNotNull(command[0]);
            switch (Operation.valueOf(command[0])){
                case ADD -> {
                    checkSecondArgument(command[1]);
                    checkThirdArgument(command[2]);
                }
                case REMOVE -> checkSecondArgument(command[2]);
                case EXPORT -> {
                }
                default -> {
                    throw new OperationInvalidException("Operation not Supported") ;
                }
            }
        }

        public static void checkSecondArgument(String secondArg){
            checkNotNull(secondArg);
            //TODO: Does it catch any number in this regex?
            if(secondArg.matches("\"-?\\\\d+\"")){
                throw new CommandInvalidException("The second argument must be a URL (String)");
            }
        }

        public static void checkThirdArgument(String thirdArg){
            checkNotNull(thirdArg);
            if(!thirdArg.matches("\"-?\\\\d+\"")){
               throw new CommandInvalidException("The third argument must be a number");
            }
        }

        public static void checkEmpty(String input){
            if(input.equals("")){
                throw new CommandInvalidException("Command is empty, need to provide a command");
            }
        }
}
