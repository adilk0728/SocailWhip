package com.newswhip.util;

public final class InputValidator {
    private InputValidator(){}

        public static <T> void checkNotNull(T input){
            if(input == null) {
                throw new NullPointerException("Input is Empty");
            }
        }

        public static void checkCommand(String[] in){
        }
}
