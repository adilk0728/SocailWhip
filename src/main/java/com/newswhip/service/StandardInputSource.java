package com.newswhip.service;

import com.newswhip.util.InputValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class StandardInputSource implements InputDataSource{
    private final BufferedReader bufferedReader;

    public StandardInputSource(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    @Override
    public CommandElement getParsedCommand() throws IOException {
       String command = bufferedReader.readLine();
       List<String> splitCommandList = Arrays.stream(command.trim().split("\\s")).toList();
       InputValidator.checkEmpty(command);
       InputValidator.runValidation(splitCommandList);
       return new CommandElement(splitCommandList);
    }
}
