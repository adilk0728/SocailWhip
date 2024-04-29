package com.newswhip.service;

import com.newswhip.exception.DuplicationEntryException;
import com.newswhip.exception.NoElementsException;
import com.newswhip.exception.OperationInvalidException;
import com.newswhip.model.Operation;
import com.newswhip.repository.Repository;
import com.newswhip.util.InputValidator;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class StandardInProcessor implements CommandProcessor{
    private BufferedReader bufferedReader;
    private Repository inMemoryDB;
    private CommandElement commandElement;
    private boolean shouldExit;

    public StandardInProcessor(BufferedReader bufferedReader, Repository db) {
        this.bufferedReader = bufferedReader;
        this.inMemoryDB = db;
    }

    @Override
    public void readAndProcess() {
        while(!shouldExit){
            String legend = """
                    ---------------------------------------
                    Enter your command --->
                    ADD/REMOVE/EXPORT/EXIT SocialURL(<protocol>//<sub-domain>.<second-level-domain>.<top-level-domain>/<path>) SCORE
                    E.g., ADD https://www.newswhip.com/ 100
                    """;
            System.out.println(legend);
            String command = "";
            try {
                command = bufferedReader.readLine();
            } catch (IOException e) {
                System.out.println("An error occurred when reading from Standard Input");
            }
            List<String> splitCommandList = Arrays.stream(command.trim().split("\\s")).toList();
            InputValidator.checkEmpty(command);
            InputValidator.runValidation(splitCommandList);
            commandElement = new CommandElement(splitCommandList);
            execute(commandElement);
        }
    }

    private void execute(CommandElement commandElement) {
        Operation op = commandElement.getOp();
        String url = commandElement.getUrl();
        int score = commandElement.getScore();
        switch(op){
            case ADD -> addUrl(url, score);
            case REMOVE -> removeUrl(url);
            case EXPORT -> {
                String header = "domain;urls;social_score";
                StringBuilder sb = new StringBuilder();
                try {
                    inMemoryDB.getAggregatedScoreForUrl().forEach((key, value) -> sb.append(key).append(';').append(value.getCount()).append(';').append(value.getScore()).append(System.lineSeparator()));
                } catch (NoElementsException e) {
                    System.out.println(e.getMessage());
                }
                System.out.printf("""
                        %s
                        %s
                        """, header, sb.toString());
            }
            case EXIT -> {
                shouldExit = true;
            }
        }
    }

    private void addUrl(String input, int score){
        try {
            inMemoryDB.addUrlWithScore(input, score);
        } catch (DuplicationEntryException e) {
            System.out.println(e.getMessage());
        }
    }

    private void removeUrl(String inputUrl){
        try {
            inMemoryDB.removeUrl(inputUrl);
        } catch (OperationInvalidException e) {
            System.out.println(e.getMessage());
        }
    }

}
