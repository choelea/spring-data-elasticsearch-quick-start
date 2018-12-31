package com.joe.springdataelasticsearch.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.joe.springdataelasticsearch.document.StoreDoc;

public interface StoreDocService {

	Page<StoreDoc> searchInName(String keyword, Pageable pageable);
	Page<StoreDoc> search(String keyword, Pageable pageable);
}
