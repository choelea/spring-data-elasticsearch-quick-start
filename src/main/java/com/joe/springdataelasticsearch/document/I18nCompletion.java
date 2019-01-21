package com.joe.springdataelasticsearch.document;

import org.springframework.data.elasticsearch.annotations.CompletionField;
import org.springframework.data.elasticsearch.core.completion.Completion;

/**
 * Just for testing
 * @author joe
 *
 */
public class I18nCompletion {
	@CompletionField(analyzer="spanish", maxInputLength=100)
	private Completion  es;
	@CompletionField(analyzer="english", maxInputLength=100)
	private Completion en;
	
	public I18nCompletion() {
		super();
	}
	public Completion getEs() {
		return es;
	}
	public I18nCompletion(String en, String es) {
		super();
		this.es = new Completion(new String[]{es});
		this.en = new Completion(new String[]{en});;
	}
	public void setEs(Completion es) {
		this.es = es;
	}
	public Completion getEn() {
		return en;
	}
	public void setEn(Completion en) {
		this.en = en;
	} 
	
	
}
