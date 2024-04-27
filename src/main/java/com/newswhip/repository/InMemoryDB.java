package com.newswhip.repository;

import com.newswhip.util.UrlParser;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public final class InMemoryDB implements Repository{
    Map<String, Integer> linkToScore;
    public static InMemoryDB INSTANCE = null;
    private InMemoryDB(Map<String, Integer> linkToScore) {
        this.linkToScore = linkToScore;
    }
    public static InMemoryDB getInstance(){
        if(INSTANCE == null){
            return new InMemoryDB(new HashMap<>());
        }
        return INSTANCE;
    }

    @Override
    public void addUrlWithScore(String inputUrl, int score) {
        if(!linkToScore.containsKey(inputUrl)){
            linkToScore.put(inputUrl, score);
        }
        //TODO Handle existing key throw exception
    }

    @Override
    public Map<String, Integer> getAggregatedScoreForUrl() {
//        return linkToScore.entrySet().stream()
//               .collect(Collectors.groupingBy(
//                       entry -> UrlParser.getSubAndTopDomain(entry.getKey()),
//                       Collectors.summingInt(Map.Entry::getValue)
//               ));
         Map<String, CountAndScore> temp = linkToScore.entrySet().stream().collect(Collectors.groupingBy(entry -> UrlParser.getSubAndTopDomain(entry.getKey()),
                                                Collectors.collectingAndThen(Collectors.toList(),
                                                        values -> {
                                                            int count = values.size();
                                                            int sum = values.stream().mapToInt(Map.Entry::getValue).sum();
                                                            return new CountAndScore(count, sum);
                                                    })));
         return null;
    }

    @Override
    public void removeUrl(String urlToDelete) {
        if(!linkToScore.containsKey(urlToDelete)){
//            TODO: throw exception
        }
        linkToScore.remove(urlToDelete);
    }

    class CountAndScore {
        private int count;
        private int score;

        public CountAndScore(int count, int score) {
            this.count = count;
            this.score = score;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }
}
