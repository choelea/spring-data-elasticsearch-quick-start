package com.joe.springdataelasticsearch.core;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import com.joe.springdataelasticsearch.document.I18nField;

public class ElasticsearchUtils {
	
	/**
	 * @deprecated 
	 * @param doc
	 * @param tokens
	 * @return
	 */
	public static int termScore(String doc, String[] tokens) {
		Assert.notNull(doc, "doc cannot be null");
	    Assert.hasLength(doc.trim(), "doc cannot be null nor empty");
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
	
	public static void scoreAndHighlightEn(I18nField field, String[] tokens) {
		Assert.notNull(field.getEn(), "field.getEn() cannot be null");
	    Assert.hasLength(field.getEn().trim(), "field.getEn() cannot be null nor empty");
	    Assert.notEmpty(tokens, "tokens cannot be empty");
	    int result = 0;
	    String[] sourceStrs = normalize(field.getEn().trim());
	    for (int i = 0; i < sourceStrs.length; i++) {
	    	for (String token : tokens) {
	    		if(sourceStrs[i].equalsIgnoreCase(token)) {	    			
	    			result ++;
	    			sourceStrs[i] = "<em>"+sourceStrs[i]+"</em>";
	    			break; // will only match one token
	    		}
			}
		}
	    field.setEn(StringUtils.join(sourceStrs, " "));
	    field.setTermScore(result);
	}
}
