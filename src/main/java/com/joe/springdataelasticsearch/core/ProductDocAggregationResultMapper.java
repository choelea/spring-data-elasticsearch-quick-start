package com.joe.springdataelasticsearch.core;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.DefaultResultMapper;
import org.springframework.stereotype.Component;

import com.joe.springdataelasticsearch.domain.AggregatedProductDocsPage;
import com.joe.springdataelasticsearch.domain.BucketData;

@Component
public class ProductDocAggregationResultMapper extends DefaultResultMapper {

	
	@Override
	public <T> Page<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
		Page<T> page = super.mapResults(response, clazz, pageable);
		AggregatedProductDocsPage<T> aggPage = new AggregatedProductDocsPage<>(page.getContent(), pageable, page.getTotalElements());
		Aggregations aggregations = response.getAggregations();
		aggPage.setBucketsByType(buildByTypeBuckets(aggregations.get(AggregatedProductDocsPage.BY_TYPE)));
		return aggPage;
	}
	
	private List<BucketData> buildByTypeBuckets(Terms terms){
		List<BucketData> list = new ArrayList<>();
		for (Bucket bucket : terms.getBuckets()) {
			list.add(new BucketData(bucket.getKeyAsString(), "type", bucket.getDocCount()));
		}
		return list;
	}
}
