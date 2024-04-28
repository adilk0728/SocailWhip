package com.newswhip.repository;

import com.newswhip.exception.DuplicationEntryException;
import com.newswhip.exception.NoElementsException;
import com.newswhip.exception.OperationInvalidException;

import java.util.Map;

public interface Repository {
    void addUrlWithScore(String inputUrl, int score) throws DuplicationEntryException;
    Map<String, InMemoryDB.CountAndScore> getAggregatedScoreForUrl() throws NoElementsException;
    void removeUrl(String urlToDelete) throws OperationInvalidException;
}
