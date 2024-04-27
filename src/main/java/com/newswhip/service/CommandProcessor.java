package com.newswhip.service;

import com.newswhip.repository.InMemoryDB;
import com.newswhip.util.InputValidator;
import com.newswhip.util.UrlParser;

import java.io.BufferedReader;
import java.io.IOException;

public class CommandProcessor {
    private BufferedReader bufferedReader;
    private String command;
    private CommandElements commandElements;

    private final InMemoryDB inMemoryDB;
    public CommandProcessor(BufferedReader bufferedReader, InMemoryDB db) {
        this.bufferedReader = bufferedReader;
        this.inMemoryDB = db;
        setCommandElements(new CommandElements());
    }
    public void setReader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    public void setCommandElements(CommandElements commandElements) {
        this.commandElements = commandElements;
    }

    public void read() throws IOException {
        while(true){
            System.out.println("Enter social URL");
            try {
               command = bufferedReader.readLine();
            } catch (IOException e) {
                throw new IOException(e.getMessage());
            }

            InputValidator.checkNotNull(command);
            String[] splitCommand = command.trim().split("\\s");


            switch(CommandElements.Operation.valueOf(splitCommand[0])){
                case ADD -> addUrl(splitCommand[1], Integer.parseInt(splitCommand[2]));
                case REMOVE -> removeUrl(splitCommand[1]);
//                case EXPORT -> System.out.println(); TODO : exporting
                case EXPORT -> System.out.println(inMemoryDB.getAggregatedScoreForUrl());
            }
            commandElements.setOp(CommandElements.Operation.valueOf(splitCommand[0]));
            commandElements.setUrl(splitCommand[1]);
            commandElements.setScore(Integer.parseInt(splitCommand[2]));

            System.out.println("SubDomain " + UrlParser.getSubdomain(command));
            System.out.println("SecondLevel " + UrlParser.getSecondLevelDomain(command));
            System.out.println("TLD " + UrlParser.getTopLevelDomain(command));
            System.out.println("Second+Top " + UrlParser.getSubAndTopDomain(command));
        }
    }

    public void addUrl(String input, int score){
        inMemoryDB.addUrlWithScore(input, score);
    }

    public void removeUrl(String inputUrl){
        inMemoryDB.removeUrl(inputUrl);
    }

    private class CommandElements{
        public Operation op;

        public enum Operation {
            ADD,
            REMOVE,
            EXPORT;
        }

        public String url;

        public int score;

        public CommandElements(){}
        public CommandElements(Operation op, String url, int score) {
            this.op = op;
            this.url = url;
            this.score = score;
        }

        public void setOp(Operation op) {
            this.op = op;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }
}
