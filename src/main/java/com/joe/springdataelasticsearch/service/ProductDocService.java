package com.joe.springdataelasticsearch.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.joe.springdataelasticsearch.document.ProductDoc;
import com.joe.springdataelasticsearch.domain.DocumentPage;

public interface ProductDocService {
	
	Page<ProductDoc> functionScoreSearch(String keyword, Boolean isSelfRun, Pageable pageable);
	Page<ProductDoc> search(String keyword, Boolean isSelfRun, Pageable pageable);

	DocumentPage<ProductDoc> aggregationSearch(String keyword, Boolean isSelfRun, Pageable pageable);
	
	List<String>  suggest(String keyword, int size);
}
