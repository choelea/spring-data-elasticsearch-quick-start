package com.joe.springdataelasticsearch.document;

import org.springframework.data.elasticsearch.core.query.IndexQuery;

public class StoreDocBuilder {
	private StoreDoc storeDoc;
	
	public StoreDocBuilder(Long id) {
		storeDoc = new StoreDoc();
		storeDoc.setId(id);
	}
	
	public StoreDocBuilder name(String name) {
		storeDoc.setName(name);
		return this;
	}
	public StoreDocBuilder description(String description) {
		storeDoc.setDescription(description);
		return this;
	}
	
	public IndexQuery buildIndex() {
		IndexQuery indexQuery = new IndexQuery();
		indexQuery.setId(storeDoc.getId().toString());
		indexQuery.setObject(storeDoc);
		return indexQuery;
	}
}
