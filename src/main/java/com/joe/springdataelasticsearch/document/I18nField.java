package com.joe.springdataelasticsearch.document;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class I18nField {

	@Field(type = FieldType.String, analyzer="english")
	private String en;

	@Field(type = FieldType.String, analyzer="spanish")
	private String es;

	@Field(type = FieldType.String, analyzer="portuguese")
	private String pt;

	@Field(type = FieldType.String, analyzer="russian")
	private String ru;
	
	public I18nField() {
		super();
	}

	public I18nField(String en, String es, String pt, String ru) {
		super();
		this.en = en;
		this.es = es;
		this.pt = pt;
		this.ru = ru;
	}

	public String getEn() {
		return en;
	}

	public void setEn(String en) {
		this.en = en;
	}

	public String getEs() {
		return es;
	}

	public void setEs(String es) {
		this.es = es;
	}

	public String getPt() {
		return pt;
	}

	public void setPt(String pt) {
		this.pt = pt;
	}

	
	public String getRu() {
		return ru;
	}

	public void setRu(String ru) {
		this.ru = ru;
	}

	@JsonIgnore
	public String getValue() {
		String language = LocaleContextHolder.getLocale().getLanguage();
		String value = null;
		switch (language) {
		case "en":
			value = getEn();
			break;
		case "es":
			value = getEs();
			break;
		case "pt":
			value = getPt();
			break;
		case "ru":
			value = getRu();
			break;
		default:
			value = getEn();
			break;
		}
		if(StringUtils.isEmpty(value)) {
			value = getEn();
		}
		return value;
	}

}
