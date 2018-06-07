package com.joe.springdataelasticsearch.domain;

import java.io.Serializable;
import java.util.List;

public class DocumentPage<T> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -510530962844910968L;
	
	public static final String BY_TYPE = "BY_TYPE";
	private List<BucketData> bucketsByType;
	private Pageable pageable;
	private List<T> products;
	public DocumentPage(List<T> content, Pageable pageable) {
		this.products = content;
		this.pageable = pageable;
	}
	
	

	public Pageable getPageable() {
		return pageable;
	}



	public void setPageable(Pageable pageable) {
		this.pageable = pageable;
	}


	public List<T> getProducts() {
		return products;
	}



	public void setProducts(List<T> products) {
		this.products = products;
	}



	public List<BucketData> getBucketsByType() {
		return bucketsByType;
	}

	public void setBucketsByType(List<BucketData> bucketsByType) {
		this.bucketsByType = bucketsByType;
	}

}
