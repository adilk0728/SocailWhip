package com.newswhip.service;

import com.newswhip.model.Operation;
import java.util.List;


class CommandElement {
    private Operation op;
    private String url;
    private int score;

    public CommandElement(){

    }
    public CommandElement(List<String> commandParts){
        setup(commandParts);
    }

    public CommandElement(Operation op){
         this(op, "", 0);
    }

    public CommandElement(Operation op, String url){
        this(op, url, 0);
    }
    public CommandElement(Operation op, String url, int score){
        this.op = op;
        this.url = url;
        this.score = score;
    }
    private void setup(List<String> commandList){
       if((commandList.size() >= 1)){
           this.op = Operation.valueOf(commandList.get(0));
       }

        if((commandList.size() >= 2)){
            this.url = commandList.get(1);
        }

        if((commandList.size() == 3)){
            this.score = Integer.parseInt(commandList.get(2));
        }
    }

    public Operation getOp() {
        return op;
    }

    public String getUrl() {
        return url;
    }

    public int getScore() {
        return score;
    }

}
