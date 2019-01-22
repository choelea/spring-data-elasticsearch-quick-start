package com.joe.springdataelasticsearch.core;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

public class ElasticsearchUtils {
	public static int termScore(String doc, String[] tokens) {
		Assert.notNull(doc, "keyword cannot be null");
	    Assert.hasLength(doc.trim(), "keyword cannot be null nor empty");
	    Assert.notEmpty(tokens, "tokens cannot be empty");
	    int result = 0;
	    for (String token : tokens) {
    		if(StringUtils.containsIgnoreCase(doc, token)) {	    			
    			result ++;
    			continue;
    		}
		}
	    return result;
	}
	
	public static String[] normalize(String keyword) {
		Assert.notNull(keyword, "keyword cannot be null");
	    Assert.hasLength(keyword.trim(), "keyword cannot be null nor empty");
	    return keyword.trim().split("\\s+");
	}
}
