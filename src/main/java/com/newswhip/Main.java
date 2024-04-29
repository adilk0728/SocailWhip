package com.newswhip;

import com.newswhip.repository.InMemoryDB;
import com.newswhip.repository.Repository;
import com.newswhip.service.CommandProcessor;
import com.newswhip.service.InputDataSource;
import com.newswhip.service.StandardInProcessor;
import com.newswhip.service.StandardInputSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args){
        Repository db = InMemoryDB.getInstance();
        InputDataSource inputDataSource = new StandardInputSource(new BufferedReader(new InputStreamReader(System.in)));
        CommandProcessor standardInProcessor = new StandardInProcessor(inputDataSource, db);
        try {
            standardInProcessor.process();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}