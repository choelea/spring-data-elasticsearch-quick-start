package com.joe.springdataelasticsearch.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import com.joe.springdataelasticsearch.core.ExtResultMapper;
import com.joe.springdataelasticsearch.document.ProductDoc;
import com.joe.springdataelasticsearch.domain.BucketData;
import com.joe.springdataelasticsearch.domain.DocumentPage;
import com.joe.springdataelasticsearch.repository.ProductDocRespository;
import com.joe.springdataelasticsearch.service.ProductDocService;
@Service
public class ProductDocServiceImpl implements ProductDocService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductDocServiceImpl.class);
	
	@Autowired
	private ProductDocRespository productDocRespository;
	
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Autowired
	private ExtResultMapper extResultMapper;
	
	@Override
	public Page<ProductDoc> search(String keyword, Boolean isSelfRun, Pageable pageable) {
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		if (StringUtils.isNotEmpty(keyword)) {
			queryBuilder.should(QueryBuilders.matchQuery(ProductDoc._name, keyword).boost(3)); // 给name字段更高的权重
			queryBuilder.should(QueryBuilders.matchQuery(ProductDoc._description, keyword));   // description 默认权重 1
			queryBuilder.minimumNumberShouldMatch(1); // 至少一个should条件满足
		}

		if (isSelfRun!=null && isSelfRun) {
			queryBuilder.must(QueryBuilders.matchQuery(ProductDoc._isSelfRun, Boolean.TRUE)); // 精准值条件查询
		}
		
				
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder)
				.withPageable(pageable).build();
		LOGGER.info("\n search(): searchContent [" + keyword + "] \n DSL  = \n " + searchQuery.getQuery().toString());
		return productDocRespository.search(searchQuery);
	}
	
	@Override
	public DocumentPage<ProductDoc> aggregationSearch(String keyword, Boolean isSelfRun, Pageable pageable) {
		TermsBuilder termBuilder = AggregationBuilders.terms(DocumentPage.BY_TYPE).field("type");
		
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		if (StringUtils.isNotEmpty(keyword)) {
			queryBuilder.should(QueryBuilders.matchQuery(ProductDoc._name, keyword).boost(3)); // 给name字段更高的权重
			queryBuilder.should(QueryBuilders.matchQuery(ProductDoc._description, keyword));   // description 默认权重 1
			queryBuilder.minimumNumberShouldMatch(1); // 至少一个should条件满足
		}

		if (isSelfRun!=null && isSelfRun) {
			queryBuilder.must(QueryBuilders.matchQuery(ProductDoc._isSelfRun, Boolean.TRUE)); // 精准值条件查询
		}
		
				
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder)
				.withPageable(pageable)
				.withHighlightFields( new HighlightBuilder.Field(ProductDoc._name).numOfFragments(1), new HighlightBuilder.Field(ProductDoc._description).forceSource(true))
				.addAggregation(termBuilder).build();
		
		LOGGER.info("\n search(): searchContent [" + keyword + "] \n DSL  = \n " + searchQuery.getQuery().toString());
		AggregatedPage<ProductDoc> page =  elasticsearchTemplate.queryForPage(searchQuery, ProductDoc.class, extResultMapper);
		
		return convert(page);
	}

	private DocumentPage<ProductDoc> convert(AggregatedPage<ProductDoc> page) {
		DocumentPage<ProductDoc> returnPage = new DocumentPage<>(page.getContent(), new com.joe.springdataelasticsearch.domain.Pageable(page.getNumber(), page.getSize()));
		returnPage.setBucketsByType(build(page.getAggregations()));
		return returnPage;
	}
	
	private List<BucketData> build(Aggregations aggregations) {
		Terms byCateTerm = aggregations.get(DocumentPage.BY_TYPE);
		List<BucketData> list = new ArrayList<>();
		for (Bucket bucket : byCateTerm.getBuckets()) {					
			list.add(new BucketData(bucket.getKeyAsString(), bucket.getKeyAsString(), bucket.getDocCount()));
		}
		return list;
	}

}
