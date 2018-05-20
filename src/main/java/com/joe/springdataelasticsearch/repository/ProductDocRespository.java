package com.joe.springdataelasticsearch.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.joe.springdataelasticsearch.document.ProductDoc;

public interface ProductDocRespository extends ElasticsearchRepository<ProductDoc, Long>{

}
