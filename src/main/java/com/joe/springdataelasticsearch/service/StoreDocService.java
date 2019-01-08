package com.joe.springdataelasticsearch.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.joe.springdataelasticsearch.document.StoreDoc;

public interface StoreDocService {

	Page<StoreDoc> searchInName(String keyword, Pageable pageable);
	
	/**
	 * Search in both name and mainproducts fields
	 * @param keyword
	 * @param pageable
	 * @return
	 */
	Page<StoreDoc> search(String keyword, Pageable pageable);
	
	/**
	 * Search in both name and mainproducts fields, using function score query
	 * @param keyword
	 * @param pageable
	 * @return
	 */
	Page<StoreDoc> searchFunctionally(String keyword, Pageable pageable);
	
	Page<StoreDoc> searchFuzzily(String keyword, PageRequest pageable);
	
	/**
	 * Search in full text to avoid cross field IDF impact; But you cannot highlight the field that are not in search fields. 
	 * @param keyword
	 * @param pageable
	 * @return
	 */
	Page<StoreDoc> searchFulltext(String keyword, PageRequest pageable);
	
	/**
	 * Search cross fields: https://www.elastic.co/guide/en/elasticsearch/guide/current/_cross_fields_queries.html
	 * @param keyword
	 * @param pageable
	 * @return
	 */
	Page<StoreDoc> searchCorssFields(String keyword, PageRequest pageable);
}
