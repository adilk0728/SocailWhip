package com.newswhip.service;

import com.newswhip.model.Operation;
import java.util.List;
import java.util.Objects;


class CommandElement {
    private Operation op;
    private String url;
    private int score;
    public CommandElement(List<String> commandParts){
        setup(commandParts);
    }

    public CommandElement(Operation op){
         this(op, null, 0);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommandElement that = (CommandElement) o;

        if (score != that.score) return false;
        if (op != that.op) return false;
        return Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        int result = op != null ? op.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + score;
        return result;
    }
}
