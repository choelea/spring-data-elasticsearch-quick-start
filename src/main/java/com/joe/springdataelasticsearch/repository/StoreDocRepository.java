package com.joe.springdataelasticsearch.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.joe.springdataelasticsearch.document.StoreDoc;

public interface StoreDocRepository extends ElasticsearchRepository<StoreDoc, Long> {
	Page<StoreDoc> findByName(String name, Pageable pageable);
}

