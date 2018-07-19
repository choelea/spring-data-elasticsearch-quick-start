package com.joe.springdataelasticsearch.listner;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

import com.joe.springdataelasticsearch.document.ProductDoc;
import com.joe.springdataelasticsearch.repository.ProductDocRespository;
 
@Component
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent>{

	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	@Autowired
	private ProductDocRespository productDocRepository;
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		elasticsearchTemplate.deleteIndex(ProductDoc.class);
		elasticsearchTemplate.createIndex(ProductDoc.class);
		elasticsearchTemplate.putMapping(ProductDoc.class);
		indexingTestData();
	}
 
	private void indexingTestData(){
		productDocRepository.save(createTestData());
	}
	
	private List<ProductDoc> createTestData(){
		List<ProductDoc> list = new ArrayList<ProductDoc>();
		list.add(new ProductDoc(0L, "Fake", "Falso", "Iphone! Iphone! Better than Iphone", "GENERAL", Boolean.FALSE));
		list.add(new ProductDoc(1L, "Mac Pro","Mac Pro", "Mac Pro is made by China", "SMARTPHONE", Boolean.TRUE));
		list.add(new ProductDoc(2L, "Huawei","Huwwei", "Made by China", "SMARTPHONE", Boolean.FALSE));
		list.add(new ProductDoc(3L, "Huawei Max3","Huawei Max3", "Huawei is designed / made by China", "SMARTPHONE", Boolean.FALSE));
		list.add(new ProductDoc(4L, "Nokia", "Nokia","Nokia better than Iphone", "GENERAL", Boolean.FALSE));
		list.add(new ProductDoc(5L, "Iphone X","Iphone X", "Iphone X is made by China", "SMARTPHONE", Boolean.TRUE));
		list.add(new ProductDoc(6L, "Nokia is Note","Nokia es Note", "Nokia is an very old brand and famous", "SMARTPHONE", Boolean.FALSE));
		list.add(new ProductDoc(7L, "Nokia N90","Nokia N90", "Nokia N 90 is made by China", "GENERAL", Boolean.FALSE));
		list.add(new ProductDoc(8L, "Vivo Z1","Vivo Z1", "Vivo is made by China, It's very popular in China.", "GENERAL", Boolean.FALSE));
		return list;
	}
	
	
    
}
