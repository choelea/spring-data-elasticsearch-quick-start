package com.joe.springdataelasticsearch.domain;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class AggregatedProductDocsPage<T> extends PageImpl<T> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -510530962844910968L;
	
	public static final String BY_TYPE = "BY_TYPE";
	private List<BucketData> bucketsByType;
	
	public AggregatedProductDocsPage(List<T> content, Pageable pageable, long total) {
		super(content, pageable, total);
	}

	public List<BucketData> getBucketsByType() {
		return bucketsByType;
	}

	public void setBucketsByType(List<BucketData> bucketsByType) {
		this.bucketsByType = bucketsByType;
	}

}
