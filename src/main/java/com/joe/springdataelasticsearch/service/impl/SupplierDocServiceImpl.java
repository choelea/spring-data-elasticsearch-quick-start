package com.joe.springdataelasticsearch.service.impl;

import java.util.Collections;
import java.util.List;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import com.joe.springdataelasticsearch.core.ElasticsearchUtils;
import com.joe.springdataelasticsearch.core.ExtResultMapper;
import com.joe.springdataelasticsearch.document.I18nField;
import com.joe.springdataelasticsearch.document.SupplierDoc;
import com.joe.springdataelasticsearch.service.SupplierDocService;

@Service
public class SupplierDocServiceImpl implements SupplierDocService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SupplierDocServiceImpl.class);
 

	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	@Autowired
	private ExtResultMapper extResultMapper;
	@Override
	public Page<SupplierDoc> search(String keyword, Pageable pageable) {
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery(); 
		queryBuilder.should(QueryBuilders.matchQuery(SupplierDoc._name, keyword));
		
		BoolQueryBuilder nestedQueryBuilder = QueryBuilders.boolQuery();
		nestedQueryBuilder.should(QueryBuilders.matchQuery("mainProducts.en", keyword));
		queryBuilder.should(QueryBuilders.nestedQuery("mainProducts", nestedQueryBuilder));
		queryBuilder.minimumNumberShouldMatch(1);
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).withPageable(pageable)
				.withHighlightFields(new HighlightBuilder.Field(SupplierDoc._name).numOfFragments(1)).build();

		LOGGER.info("\n search(): searchContent [" + keyword + "] \n DSL  = \n " + searchQuery.getQuery().toString());
		Page<SupplierDoc> page = elasticsearchTemplate.queryForPage(searchQuery, SupplierDoc.class, extResultMapper);
		sortNestedObject(page.getContent(), keyword);
		return page;
	}
	
	private void sortNestedObject(List<SupplierDoc> list, String keyword) {
		String[] tokens = ElasticsearchUtils.normalize(keyword);
		for (SupplierDoc supplierDoc : list) {
			for (I18nField field : supplierDoc.getMainProducts()) {
				ElasticsearchUtils.scoreAndHighlightEn(field, tokens);
			}
			Collections.sort(supplierDoc.getMainProducts());
		}	
	}
}
