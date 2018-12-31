package com.joe.springdataelasticsearch.listner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Component;

import com.joe.springdataelasticsearch.document.ProductDoc;
import com.joe.springdataelasticsearch.document.StoreDoc;
import com.joe.springdataelasticsearch.document.StoreDocBuilder;
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
		
		elasticsearchTemplate.deleteIndex(StoreDoc.class);
		elasticsearchTemplate.createIndex(StoreDoc.class);
		elasticsearchTemplate.putMapping(StoreDoc.class);
		createTestData();
	}
 
	private void createTestData(){
		createProductDocs();
		createStoreDocs();
	}
	
	private void createStoreDocs() {
		IndexQuery store1 = new StoreDocBuilder(0l).name("HuaWei Authorized Shop").mainProducts("Smart Phone").buildIndex(); 
		elasticsearchTemplate.index(store1);
		elasticsearchTemplate.index(new StoreDocBuilder(0l).name("XiaoMi Authorized Shop").mainProducts("Smart Phone").buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(1l).name("Oppo Authorized WuHan Shop Owned by Joe").mainProducts("Smart Phone").buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(2l).name("Meizu Authorized Shop").mainProducts("Smart Phone").buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(3l).name("Sung Authorized Shop").mainProducts("Smart Phone").buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(4l).name("Vivo Authorized Shop").mainProducts("Smart Phone").buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(5l).name("Lenovo Authorized  Shop").mainProducts("Smart Phone").buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(6l).name("Sony Authorized VIP Shop").mainProducts("Smart Phone").buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(7l).name("Apple Store").mainProducts("Ipad, Mac Pro").buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(8l).name("Samsung Authorized Shop").mainProducts("Smart Phone").buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(9l).name("Smart Shop").mainProducts("Smart Phone").buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(10l).name("Mark's Mobile Shop").mainProducts("Smart Phone").buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(11l).name("Jane's Mobile Shop").mainProducts("Smart Phone").buildIndex());
	}

	private void createProductDocs(){
		List<ProductDoc> list = new ArrayList<ProductDoc>();
		list.add(new ProductDoc(0L, "Fake", "Falso", "Iphone! Iphone! Better than Iphone", "GENERAL", Boolean.FALSE, DateUtils.addDays(new Date(), 10)));
		list.add(new ProductDoc(1L, "Mac Pro","Mac Pro", "Mac Pro is made by China", "SMARTPHONE", Boolean.TRUE, DateUtils.addDays(new Date(), 3)));
		list.add(new ProductDoc(2L, "Huawei","Huwwei", "Made by China", "SMARTPHONE", Boolean.FALSE, DateUtils.addDays(new Date(), 100)));
		list.add(new ProductDoc(3L, "Huawei Max3","Huawei Max3", "Huawei is designed / made by China", "SMARTPHONE", Boolean.FALSE, DateUtils.addDays(new Date(), -10)));
		list.add(new ProductDoc(4L, "Nokia", "Nokia","Nokia better than Iphone", "GENERAL", Boolean.FALSE, DateUtils.addDays(new Date(), 10)));
		list.add(new ProductDoc(5L, "Iphone X","Iphone X", "Iphone X is made by China", "SMARTPHONE", Boolean.TRUE, DateUtils.addDays(new Date(), -20)));
		list.add(new ProductDoc(6L, "Nokia is Note","Nokia es Note", "Nokia is an very old brand and famous", "SMARTPHONE", Boolean.FALSE, DateUtils.addDays(new Date(), -40)));
		list.add(new ProductDoc(7L, "Nokia N90","Nokia N90", "Nokia N 90 is made by China", "GENERAL", Boolean.FALSE, DateUtils.addDays(new Date(), -10)));
		list.add(new ProductDoc(8L, "Vivo Z1","Vivo Z1", "Vivo is made by China, It's very popular in China.", "GENERAL", Boolean.FALSE, DateUtils.addDays(new Date(), 60)));
		list.add(new ProductDoc(9L, "Huawei Max3","Huawei Max3", "is designed / made by China", "SMARTPHONE", Boolean.FALSE, DateUtils.addDays(new Date(), 1)));
		productDocRepository.save(list);
	}
}
