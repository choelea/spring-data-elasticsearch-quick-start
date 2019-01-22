package com.joe.springdataelasticsearch.document;

import org.springframework.data.elasticsearch.core.query.IndexQuery;

import com.google.common.collect.Lists;

public class SupplierDocBuilder {
	private SupplierDoc supplierDoc;
	
	public SupplierDocBuilder(Long id) {
		supplierDoc = new SupplierDoc();
		supplierDoc.setId(id);
	}
	
	public SupplierDocBuilder name(String name) {
		supplierDoc.setName(name);
		return this;
	}
	public SupplierDocBuilder mainProducts(I18nField ... i18ns) {		
		supplierDoc.setMainProducts(Lists.newArrayList(i18ns));
		return this;
	}

	public IndexQuery buildIndex() {
		IndexQuery indexQuery = new IndexQuery();
		indexQuery.setId(supplierDoc.getId().toString());
		indexQuery.setObject(supplierDoc);
		return indexQuery;
	}
}
