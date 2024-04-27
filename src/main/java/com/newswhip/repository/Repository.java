package com.newswhip.repository;

import java.util.Map;

public interface Repository {
    void addUrlWithScore(String inputUrl, int score);
    Map<String, Integer> getAggregatedScoreForUrl();
    void removeUrl(String urlToDelete);
}
