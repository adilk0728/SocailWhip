package com.newswhip.repository;

import com.newswhip.exception.DuplicationEntryException;
import com.newswhip.exception.NoElementsException;
import com.newswhip.exception.OperationInvalidException;
import com.newswhip.util.UrlParser;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public final class InMemoryDB implements Repository{
    private final Map<String, Integer> linkToScore;
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
    public void addUrlWithScore(String inputUrl, int score) throws DuplicationEntryException {
        if(!linkToScore.containsKey(inputUrl)){
            linkToScore.put(inputUrl, score);
        } else {
            throw new DuplicationEntryException("This URL has already been added");
        }
    }

    @Override
    public Map<String, CountAndScore> getAggregatedScoreForUrl() throws NoElementsException {
        if(linkToScore.isEmpty()){
            throw new NoElementsException("There are no elements to be exported");
        }

         return  linkToScore.entrySet().stream().collect(Collectors.groupingBy(entry -> UrlParser.getSubAndTopDomain(entry.getKey()),
                Collectors.collectingAndThen(Collectors.toList(),
                        values -> {
                            int count = values.size();
                            int sum = values.stream().mapToInt(Map.Entry::getValue).sum();
                            return new CountAndScore(count, sum);
                        })));
    }

    @Override
    public void removeUrl(String urlToDelete) throws OperationInvalidException {
        if(!linkToScore.containsKey(urlToDelete)){
            throw new OperationInvalidException("Either there is no value mapped to this url or REMOVE command is run before ADD");
        }
        linkToScore.remove(urlToDelete);
    }

    public static class CountAndScore {
        private final int count;
        private final int score;

        public CountAndScore(int count, int score) {
            this.count = count;
            this.score = score;
        }

        public int getCount() {
            return count;
        }

        public int getScore() {
            return score;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            CountAndScore that = (CountAndScore) o;

            if (count != that.count) return false;
            return score == that.score;
        }

        @Override
        public int hashCode() {
            int result = count;
            result = 31 * result + score;
            return result;
        }
    }
}
