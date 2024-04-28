package com.newswhip;

import com.newswhip.repository.InMemoryDB;
import com.newswhip.repository.Repository;
import com.newswhip.service.CommandProcessor;
import com.newswhip.service.StandardInProcessor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args){
        Repository db = InMemoryDB.getInstance();
        CommandProcessor standardInProcessor = new StandardInProcessor(new BufferedReader(new InputStreamReader(System.in)), db);
        try {
            standardInProcessor.readAndProcess();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}