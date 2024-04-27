package com.newswhip.util;

public final class UrlParser {

    private UrlParser(){}

    public static String getSubdomain(String inputUrl){
       int indexOfProtoEnd =  inputUrl.indexOf('/');
       int indexOfSubDomainEnd = inputUrl.indexOf('.', indexOfProtoEnd) - 1;
       return inputUrl.substring(indexOfProtoEnd + 2, indexOfSubDomainEnd + 1);
    }

    public static String getSecondLevelDomain(String inputUrl){
       int indexOfSubDomainEnd = inputUrl.indexOf('.') - 1;
       int indexOfSecondLvlEnd = inputUrl.indexOf('.', indexOfSubDomainEnd + 2) - 1;
       return inputUrl.substring(indexOfSubDomainEnd + 2, indexOfSecondLvlEnd + 1);
    }
    public static String getTopLevelDomain(String inputUrl){
        int indexOfSubDomainEnd = inputUrl.indexOf('.') - 1;
        int indexOfSecondLvlEnd = inputUrl.indexOf('.', indexOfSubDomainEnd + 2) - 1;
        int indexOfTldEnd = inputUrl.indexOf('/', indexOfSecondLvlEnd + 2) - 1;
        return inputUrl.substring(indexOfSecondLvlEnd + 2, indexOfTldEnd + 1);
    }

    public static String getSubAndTopDomain(String inputUrl){
        return String.format(getSecondLevelDomain(inputUrl) + "%s" + getTopLevelDomain(inputUrl), ".");
    }
}
