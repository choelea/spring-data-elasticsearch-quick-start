package com.joe.springdataelasticsearch.domain;

import java.io.Serializable;

public class BucketData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8837185832164979987L;
	private String key;
	private String lable;
	private Long docCount;

	public BucketData(String key, String lable, Long docCount) {
		super();
		this.key = key;
		this.lable = lable;
		this.docCount = docCount;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getLable() {
		return lable;
	}

	public void setLable(String lable) {
		this.lable = lable;
	}

	public Long getDocCount() {
		return docCount;
	}

	public void setDocCount(Long docCount) {
		this.docCount = docCount;
	}

}