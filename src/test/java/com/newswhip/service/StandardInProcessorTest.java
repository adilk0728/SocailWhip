package com.newswhip.service;

import com.newswhip.exception.DuplicationEntryException;
import com.newswhip.exception.NoElementsException;
import com.newswhip.repository.InMemoryDB;
import com.newswhip.repository.Repository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

public class StandardInProcessorTest {
    static Map<String, Integer> linksScore;
    static CommandProcessor commandProcessor;
    static Repository mockDb;
    static BufferedReader bufferedReader;
    static InputDataSource inputDataSource;
   @BeforeAll
   static void setupTestData(){
       linksScore = new HashMap<>();
       bufferedReader = mock(BufferedReader.class);
       inputDataSource = new StandardInputSource(bufferedReader);
       mockDb = mock(InMemoryDB.class);
       commandProcessor = new StandardInProcessor(inputDataSource, mockDb);
   }
   @Test
   void check_if_record_added_to_map() {
       try {
           when(bufferedReader.readLine()).thenReturn("ADD http://www.rte.ie/news/politics/2018/1004/1001034-cso/ 20");
           when(bufferedReader.readLine()).thenReturn("EXIT");
       } catch (IOException e) {
           System.out.println("Error reading"+e.getMessage());
       }
        addUrlMock("http://www.rte.ie/news/politics/2018/1004/1001034-cso/", 20);
       try {
           commandProcessor.process();
           for(Map.Entry<String, Integer> entry : linksScore.entrySet()){
               assert(entry.getKey()).equals("http://www.rte.ie/news/politics/2018/1004/1001034-cso/");
               assert(entry.getValue()).equals(20);
           }
       } catch (IOException e) {
           System.out.println(e.getMessage());
       }
   }
    @Test
    void check_add_of_two_urls_of_same_second_top_domain() throws NoElementsException {
        try {
            when(bufferedReader.readLine()).thenReturn("ADD https://www.rte.ie/news/ulster/2018/1004/1000952-moanghan-mine/ 30");
            when(bufferedReader.readLine()).thenReturn("EXIT");
        } catch (IOException e) {
            System.out.println("Error reading"+e.getMessage());
        }

        addUrlMock("https://www.rte.ie/news/ulster/2018/1004/1000952-moanghan-mine/", 30);
        try {
            when(mockDb.getAggregatedScoreForUrl()).thenReturn(Map.of("rte.ie", new InMemoryDB.CountAndScore(2, 50)));
        } catch (NoElementsException e) {
            System.out.println(e.getMessage());
        }

        try {
            commandProcessor.process();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        assert(mockDb.getAggregatedScoreForUrl()).equals(Map.of("rte.ie", new InMemoryDB.CountAndScore(2, 50)));
    }
   @Test
   void check_if_the_given_url_is_deleted(){
        try {
            when(bufferedReader.readLine()).thenReturn("REMOVE http://www.rte.ie/news/politics/2018/1004/1001034-cso/");
            when(bufferedReader.readLine()).thenReturn("EXIT");
        } catch (IOException e) {
            System.out.println("Error reading"+e.getMessage());
        }

        doAnswer((invocation) -> linksScore.remove(invocation.getArgument(0)))
                    .when(mockDb)
                    .removeUrl("http://www.rte.ie/news/politics/2018/1004/1001034-cso/");

        try {
            commandProcessor.process();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
       assert(!linksScore.containsKey("http://www.rte.ie/news/politics/2018/1004/1001034-cso/"));
   }
    private void addUrlMock(String url, int score){
        try {
            doAnswer((invocation) -> linksScore.put(invocation.getArgument(0), invocation.getArgument(1)))
                    .when(mockDb)
                    .addUrlWithScore(url, score);
        } catch (DuplicationEntryException e) {
            System.out.println(e.getMessage());
        }
    }
}
