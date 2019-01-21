package com.joe.springdataelasticsearch.document;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Define Document 'product-index', 如果不指定type，会使用class的名称作为type， 5.0 后的版本是没有type的。
 * @author joe
 *
 */
@Document(indexName="product-index", type="main")
public class ProductDoc implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7204953593090241813L;
	
	public ProductDoc() {} // mandatory for Json Mapping

	public ProductDoc(Long id, String name, String esname, String description, String type, Boolean isSelfRun, Date updated) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.type = type;
		this.isSelfRun = isSelfRun;
		this.suggest=new I18nCompletion(name,esname);
		this.updatedDate = updated;
	}

	
	@Id
	@Field(type=FieldType.Long, index=FieldIndex.not_analyzed)
	private Long id;
	
	@Field(type=FieldType.String,  analyzer="english")
	private String name;
	public static final String _name="name";
	
	
	@Field(type=FieldType.Nested)// cannot be Object? Needs to investigate
	private I18nCompletion suggest;
	public static final String _suggest="suggest";
	
	
	@Field(type=FieldType.String, analyzer="english")
	private String description;
	public static final String _description="description";
	
	
	@Field(type=FieldType.String, index=FieldIndex.not_analyzed)
	private String type;
	public static final String _type="type";
	
	
	@Field(type=FieldType.Boolean, index = FieldIndex.not_analyzed)
	private Boolean isSelfRun;
	public static final String _isSelfRun="isSelfRun";
	
	
	@Field(type=FieldType.Date, index = FieldIndex.not_analyzed)
	private Date updatedDate;
	public static final String _updatedDate = "updatedDate";
	public Boolean getIsSelfRun() {
		return isSelfRun;
	}

	public void setIsSelfRun(Boolean isSelfRun) {
		this.isSelfRun = isSelfRun;
	}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public I18nCompletion getSuggest() {
		return suggest;
	}

	public void setSuggest(I18nCompletion suggest) {
		this.suggest = suggest;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
}
