package com.joe.springdataelasticsearch.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.joe.springdataelasticsearch.document.SupplierDoc;

public interface SupplierDocService {

	/**
	 * Search in both name and mainproducts fields
	 * @param keyword
	 * @param pageable
	 * @return
	 */
	Page<SupplierDoc> search(String keyword, Pageable pageable);
}
