package com.newswhip.service;

import com.newswhip.exception.DuplicationEntryException;
import com.newswhip.exception.NoElementsException;
import com.newswhip.exception.OperationInvalidException;
import com.newswhip.model.Operation;
import com.newswhip.repository.Repository;

import java.io.IOException;
import java.util.Objects;

public class StandardInProcessor implements CommandProcessor{
    private final Repository inMemoryDB;
    private boolean shouldExit;
    private InputDataSource inputDataSource;

    public StandardInProcessor(InputDataSource inputDataSource, Repository inMemoryDB) {
        this.inputDataSource = inputDataSource;
        this.inMemoryDB = inMemoryDB;
    }

    @Override
    public void process() {
        while(!shouldExit){
            String legend = """
                    ---------------------------------------
                    Enter your command --->
                    ADD/REMOVE/EXPORT/EXIT SocialURL(<protocol>//<sub-domain>.<second-level-domain>.<top-level-domain>/<path>) SCORE
                    E.g., ADD https://www.newswhip.com/ 100
                    """;
            System.out.println(legend);
            CommandElement commandElement = null;
            try {
                 commandElement = inputDataSource.getParsedCommand();
            } catch (IOException e) {
                System.out.println("An error occurred when reading from Standard Input");
            }
            execute(Objects.requireNonNull(commandElement));
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
                        """, header, sb);
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
