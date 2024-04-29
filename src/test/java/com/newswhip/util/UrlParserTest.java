package com.newswhip.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UrlParserTest {
    @ParameterizedTest
    @MethodSource("stream_Of_TLD_com")
    void check_that_topLevelDomain_is_equalTo_com(String input){
        assert(UrlParser.getTopLevelDomain(input)).equals("com");
    }
    @ParameterizedTest
    @MethodSource("stream_Of_TLD_ie")
    void check_that_topLevelDomain_is_equalTo_ie(String input){
        assert(UrlParser.getTopLevelDomain(input)).equals("ie");
    }
    @ParameterizedTest
    @MethodSource("stream_Of_TLD_ie")
    void check_that_secondLevelDomain_is_equalTo_rte(String input){
        assert(UrlParser.getSecondLevelDomain(input)).equals("rte");
    }
    @ParameterizedTest
    @MethodSource("stream_Of_TLD_ie")
    void check_that_second_added_topLevelDomain_is_equalTo_rte_ie(String input){
        assert(UrlParser.getSubAndTopDomain(input)).equals("rte.ie");
    }

    @ParameterizedTest
    @MethodSource("stream_Of_TLD_com")
    void check_that_subDomain_is_equalTo_www(String input){
        assert(UrlParser.getSubdomain(input)).equals("www");
    }

    @Test
    @DisplayName("Parsing TLD without / throws exception")
    void check_that_missing_trailing_forward_slash_throws_exception(){
        String missingTrailingSlashUrl = "https://www.google.com";
        assertThrows(StringIndexOutOfBoundsException.class, () -> UrlParser.getTopLevelDomain(missingTrailingSlashUrl));
    }

    static Stream<String> stream_Of_TLD_com(){
        return Stream.of("https://www.newswhip.com/",
                "https://www.google.com/",
                "http://www.bbc.com/news/world-europe-45746837");
    }
    static Stream<String> stream_Of_TLD_ie(){
        return Stream.of("http://www.rte.ie/news/politics/2018/1004/1001034-cso/",
                "https://www.rte.ie/news/ulster/2018/1004/1000952-moanghan-mine/");
    }
}
