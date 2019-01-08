package com.joe.springdataelasticsearch.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.joe.springdataelasticsearch.document.StoreDoc;

public interface StoreDocRepository extends ElasticsearchRepository<StoreDoc, Long> {
	
	/**
	 * 查找出文档，这些文档在name字段包含 传入的keyword分词后的所有单词
	 * @param keyword
	 * @param pageable
	 * @return
	 */
	Page<StoreDoc> findByName(String keyword, Pageable pageable);
}

