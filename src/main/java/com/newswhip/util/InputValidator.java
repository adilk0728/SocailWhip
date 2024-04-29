package com.newswhip.util;

import com.newswhip.exception.CommandInvalidException;
import com.newswhip.exception.OperationInvalidException;

import java.util.List;

public final class InputValidator {
        private InputValidator(){}

        private static <T> void checkNotNull(T input){
            if(input == null) {
                throw new NullPointerException("Input cannot be Empty");
            }
        }

        public static void runValidation(List<String> command) throws OperationInvalidException, CommandInvalidException {
            checkNotNull(command.get(0));
            String opsString = command.get(0);
            switch (opsString){
                case "ADD" -> {
                    checkCompleteness(command, 3);
                    checkSecondArgument(command.get(1));
                    checkThirdArgument(command.get(2));
                }
                case "REMOVE" -> {
                    checkCompleteness(command, 2);
                    checkSecondArgument(command.get(1));
                }
                case "EXPORT", "EXIT" -> {
                    checkCompleteness(command, 1);
                }
                default -> {
                    throw new OperationInvalidException("Operation not Supported") ;
                }
            }
        }

        private static void checkSecondArgument(String secondArg) throws OperationInvalidException {
            checkNotNull(secondArg);
            //TODO: Does it catch any number in this regex?
            if(secondArg.matches("-?\\d+")){
                throw new OperationInvalidException("The second argument must be a URL (String)");
            }
        }

        private static void checkThirdArgument(String thirdArg) throws OperationInvalidException {
            checkNotNull(thirdArg);
            if(!thirdArg.matches("-?\\d+")){
               throw new OperationInvalidException("The third argument must be a number");
            }
        }

        public static void checkEmpty(String input) throws OperationInvalidException {
            if(input.equals("")){
                throw new OperationInvalidException("Command is empty, need to provide a command");
            }
        }

        private static void checkCompleteness(List<String> inputCommandList, int expectedLength) throws CommandInvalidException {
            if(inputCommandList.size() != expectedLength){
                throw new CommandInvalidException("The command is either missing arguments or has too many");
            }
        }
}
