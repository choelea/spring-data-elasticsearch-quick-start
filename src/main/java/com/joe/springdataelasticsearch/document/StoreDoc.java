package com.joe.springdataelasticsearch.document;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "store", type = "doc")
public class StoreDoc implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1523814535189878438L;

	public StoreDoc() {
	} // mandatory for Json Mapping

	public StoreDoc(Long id, String name, String mainProducts, String type, Boolean isSelfRun, Date updated) {
		super();
		this.id = id;
		this.name = name;
		this.mainProducts = mainProducts;
	}

	@Id
	@Field(type = FieldType.Long, index = FieldIndex.not_analyzed)
	private Long id;

	@Field(type = FieldType.String,  analyzer = "whitespace")
	private String name;
	public static final String _name = "name";

	@Field(type = FieldType.String, analyzer = "whitespace")
	private String mainProducts;
	public static final String _mainProducts = "mainProducts";

	@Field(type = FieldType.String,  analyzer = "whitespace")
	private String fullText;
	public static final String _fullText = "fullText";
	
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

	public String getMainProducts() {
		return mainProducts;
	}

	public void setMainProducts(String mainProducts) {
		this.mainProducts = mainProducts;
	}

	public String getFullText() {
		return fullText;
	}

	public void setFullText(String fullText) {
		this.fullText = fullText;
	}

	@Override
	public String toString() {
		return "StoreDoc [id=" + id + ", name=" + name + ", mainProducts=" + mainProducts + "]";
	}	
}
