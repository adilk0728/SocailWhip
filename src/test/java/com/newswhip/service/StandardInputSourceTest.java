package com.newswhip.service;

import com.newswhip.model.Operation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StandardInputSourceTest {

    static BufferedReader mockBufferedReader;
    static InputDataSource standardIs;
    @BeforeAll
    static void setup(){
       mockBufferedReader = mock(BufferedReader.class);
       standardIs = new StandardInputSource(mockBufferedReader);
    }
    @Test
    void check_if_add_command_is_parsed(){
        CommandElement expectedCE = new CommandElement(Operation.ADD, "http://www.rte.ie/news/politics/2018/1004/1001034-cso/", 20);
        String command = "ADD http://www.rte.ie/news/politics/2018/1004/1001034-cso/ 20";
        CommandElement actualCE = null;
        try {
            when(mockBufferedReader.readLine()).thenReturn(command);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try {
            actualCE = standardIs.getParsedCommand();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        assert Objects.equals(actualCE, expectedCE);
    }

    @Test
    void check_if_remove_command_is_parsed(){
        CommandElement expectedCE = new CommandElement(Operation.REMOVE, "http://www.rte.ie/news/politics/2018/1004/1001034-cso/");
        String command = "REMOVE http://www.rte.ie/news/politics/2018/1004/1001034-cso/";
        CommandElement actualCE = null;
        try {
            when(mockBufferedReader.readLine()).thenReturn(command);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try {
            actualCE = standardIs.getParsedCommand();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        assert Objects.equals(actualCE, expectedCE);
    }
    @Test
    void check_if_export_command_is_parsed(){
        CommandElement expectedCE = new CommandElement(Operation.EXPORT);
        String command = "EXPORT";
        CommandElement actualCE = null;
        try {
            when(mockBufferedReader.readLine()).thenReturn(command);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try {
            actualCE = standardIs.getParsedCommand();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        assert Objects.equals(actualCE, expectedCE);
    }
    @Test
    void check_if_exit_command_is_parsed(){
        CommandElement expectedCE = new CommandElement(Operation.EXIT);
        String command = "EXIT";
        CommandElement actualCE = null;
        try {
            when(mockBufferedReader.readLine()).thenReturn(command);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try {
            actualCE = standardIs.getParsedCommand();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        assert Objects.equals(actualCE, expectedCE);
    }
}
