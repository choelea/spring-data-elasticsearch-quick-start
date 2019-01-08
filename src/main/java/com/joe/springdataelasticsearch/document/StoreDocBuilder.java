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
	public StoreDocBuilder mainProducts(String mainProducts) {
		storeDoc.setMainProducts(mainProducts);
		return this;
	}
	
	public StoreDocBuilder fullText() {
		storeDoc.setFullText(storeDoc.getName()+" "+storeDoc.getMainProducts());
		return this;
	}
	public IndexQuery buildIndex() {
		IndexQuery indexQuery = new IndexQuery();
		indexQuery.setId(storeDoc.getId().toString());
		indexQuery.setObject(storeDoc);
		return indexQuery;
	}
}
