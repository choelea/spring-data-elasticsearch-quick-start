package com.joe.springdataelasticsearch.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;
import org.elasticsearch.search.highlight.HighlightField;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.ElasticsearchException;
import org.springframework.data.elasticsearch.annotations.ScriptedField;
import org.springframework.data.elasticsearch.core.AbstractResultMapper;
import org.springframework.data.elasticsearch.core.DefaultEntityMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

@Component
public class ExtResultMapper extends AbstractResultMapper {

	 
	public ExtResultMapper() {
		super(new DefaultEntityMapper());
	}
 

	@Override
	public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
		long totalHits = response.getHits().totalHits();
		List<T> results = new ArrayList<T>();
		for (SearchHit hit : response.getHits()) {
			if (hit != null) {
				T result = null;
				if (StringUtils.isNotBlank(hit.sourceAsString())) {
					result = mapEntity(hit.sourceAsString(), clazz);
				} else {
					result = mapEntity(hit.getFields().values(), clazz);
				}
				populateScriptFields(result, hit);
				populateHighLightedFields(result, hit.getHighlightFields());
				results.add(result);
			}
		}

		return new AggregatedPageImpl<T>(results, pageable, totalHits, response.getAggregations());
	}

	private <T>  void populateHighLightedFields(T result, Map<String, HighlightField> highlightFields) {
		for (HighlightField field : highlightFields.values()) {
			try {
				PropertyUtils.setProperty(result, field.getName(), field.getFragments()[0].toString());
			} catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
				throw new ElasticsearchException("failed to set highlighted value for field: " + field.getName()
						+ " with value: " + field.getFragments(), e);
			} 
		}		
	}


	private <T> void populateScriptFields(T result, SearchHit hit) {
		if (hit.getFields() != null && !hit.getFields().isEmpty() && result != null) {
			for (java.lang.reflect.Field field : result.getClass().getDeclaredFields()) {
				ScriptedField scriptedField = field.getAnnotation(ScriptedField.class);
				if (scriptedField != null) {
					String name = scriptedField.name().isEmpty() ? field.getName() : scriptedField.name();
					SearchHitField searchHitField = hit.getFields().get(name);
					if (searchHitField != null) {
						field.setAccessible(true);
						try {
							field.set(result, searchHitField.getValue());
						} catch (IllegalArgumentException e) {
							throw new ElasticsearchException("failed to set scripted field: " + name + " with value: "
									+ searchHitField.getValue(), e);
						} catch (IllegalAccessException e) {
							throw new ElasticsearchException("failed to access scripted field: " + name, e);
						}
					}
				}
			}
		}
	}


	private <T> T mapEntity(Collection<SearchHitField> values, Class<T> clazz) {
		return mapEntity(buildJSONFromFields(values), clazz);
	}

	private String buildJSONFromFields(Collection<SearchHitField> values) {
		JsonFactory nodeFactory = new JsonFactory();
		try {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			JsonGenerator generator = nodeFactory.createGenerator(stream, JsonEncoding.UTF8);
			generator.writeStartObject();
			for (SearchHitField value : values) {
				if (value.getValues().size() > 1) {
					generator.writeArrayFieldStart(value.getName());
					for (Object val : value.getValues()) {
						generator.writeObject(val);
					}
					generator.writeEndArray();
				} else {
					generator.writeObjectField(value.getName(), value.getValue());
				}
			}
			generator.writeEndObject();
			generator.flush();
			return new String(stream.toByteArray(), Charset.forName("UTF-8"));
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public <T> T mapResult(GetResponse response, Class<T> clazz) {
		T result = mapEntity(response.getSourceAsString(), clazz);
		 return result;
	}

	@Override
	public <T> LinkedList<T> mapResults(MultiGetResponse responses, Class<T> clazz) {
		LinkedList<T> list = new LinkedList<T>();
		for (MultiGetItemResponse response : responses.getResponses()) {
			if (!response.isFailed() && response.getResponse().isExists()) {
				T result = mapEntity(response.getResponse().getSourceAsString(), clazz);
				list.add(result);
			}
		}
		return list;
	}

	 
}
