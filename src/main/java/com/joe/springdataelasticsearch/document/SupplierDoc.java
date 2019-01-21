package com.joe.springdataelasticsearch.document;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "supplier", type = "doc")
public class SupplierDoc implements Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1771788891355351392L;

	public SupplierDoc() {
	} // mandatory for Json Mapping

	public SupplierDoc(Long id, String name, I18nField[] mainProducts, String type, Boolean isSelfRun, Date updated) {
		super();
		this.id = id;
		this.name = name;
		this.mainProducts = mainProducts;
	}

	@Id
	@Field(type = FieldType.Long, index = FieldIndex.not_analyzed)
	private Long id;

	@Field(type = FieldType.String, analyzer = "english")
	private String name;
	public static final String _name = "name";

	@Field(type = FieldType.Nested, analyzer = "english")
	private I18nField[] mainProducts;
	public static final String _mainProducts_en = "mainProducts.en";

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	

	
	 
	public I18nField[] getMainProducts() {
		return mainProducts;
	}

	public void setMainProducts(I18nField[] mainProducts) {
		this.mainProducts = mainProducts;
	}

	@Override
	public String toString() {
		return "SupplierDoc [id=" + id + ", name=" + name + ", mainProducts=" + Arrays.toString(mainProducts) + "]";
	}
}
