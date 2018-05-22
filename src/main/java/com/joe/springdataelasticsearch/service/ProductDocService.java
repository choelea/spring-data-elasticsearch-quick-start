package com.joe.springdataelasticsearch.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.joe.springdataelasticsearch.document.ProductDoc;

public interface ProductDocService {
	Page<ProductDoc> search(String keyword, Boolean isSelfRun, Pageable pageable);
}
