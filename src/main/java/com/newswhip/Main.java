package com.newswhip;

import com.newswhip.repository.InMemoryDB;
import com.newswhip.service.CommandProcessor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args){
        InMemoryDB db = InMemoryDB.getInstance();
        CommandProcessor commandProcessor = new CommandProcessor(new BufferedReader(new InputStreamReader(System.in)), db);
        try {
            commandProcessor.read();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}