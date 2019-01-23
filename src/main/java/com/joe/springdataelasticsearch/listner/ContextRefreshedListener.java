package com.joe.springdataelasticsearch.listner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

import com.joe.springdataelasticsearch.document.I18nField;
import com.joe.springdataelasticsearch.document.ProductDoc;
import com.joe.springdataelasticsearch.document.StoreDoc;
import com.joe.springdataelasticsearch.document.StoreDocBuilder;
import com.joe.springdataelasticsearch.document.SupplierDoc;
import com.joe.springdataelasticsearch.document.SupplierDocBuilder;
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
		
		
		elasticsearchTemplate.deleteIndex(SupplierDoc.class);
		elasticsearchTemplate.createIndex(SupplierDoc.class);
		elasticsearchTemplate.putMapping(SupplierDoc.class);
		createTestData();
	}
 
	private void createTestData(){
		createProductDocs();
		createStoreDocs();
		createSupplierDocs();
	}
	
	private void createSupplierDocs() {		
	    
		I18nField  apple7Plug = new I18nField("Apple 7 Plus", "Apple Plus", null, null);
		I18nField  samsungs9 = new I18nField("Samsung Galaxy S9", "Apple Plus", null, null);
		I18nField  samsungs8 = new I18nField("Samsung Galaxy S8", "Apple Plus", null, null);
		I18nField  apple6Plus = new I18nField("Apple 6 plus", "Apple Plus", null, null);
		I18nField  appleXPlus = new I18nField("Apple X", "Apple Plus", null, null);
		
		elasticsearchTemplate.index(new SupplierDocBuilder(0l).name("Apple Authorized Shop").mainProducts(apple7Plug, samsungs9).buildIndex());
		elasticsearchTemplate.index(new SupplierDocBuilder(1l).name("Shop Owned by Joe").mainProducts(samsungs8, apple7Plug).buildIndex());
		elasticsearchTemplate.index(new SupplierDocBuilder(2l).name("Jiu-shu Shop").mainProducts(apple7Plug, samsungs9, samsungs8, apple6Plus, appleXPlus).buildIndex());
		elasticsearchTemplate.index(new SupplierDocBuilder(3l).name("Sung Authorized Shop").mainProducts(samsungs8).buildIndex());
		
	}

	private void createStoreDocs() {
		elasticsearchTemplate.index(new StoreDocBuilder(0l).name("XiaoMi Authorized Shop").mainProducts("Smart Phone").fullText().buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(1l).name("Oppo Authorized Owned by Joe in Wuhan Shop").mainProducts("Smart Phone").fullText().buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(2l).name("Meizu Authorized Shop Double Authorized").mainProducts("Smart Phone").fullText().buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(3l).name("Sung Authorized Shop").mainProducts("Smart Phone , Stupid Phone").fullText().buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(4l).name("Vivo Authorized Shop").mainProducts("Smart Phone").fullText().buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(5l).name("Lenovo Authorized  Shop").mainProducts("Stupid Phone").fullText().buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(6l).name("Sony Authorized VIP Shop").mainProducts("Stupid Phone").fullText().buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(7l).name("Apple Store").mainProducts("Ipad, Mac Pro").fullText().buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(8l).name("Samsung Authorized Shop").mainProducts("Smart Phone").fullText().buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(9l).name("Smart Shop").mainProducts("Smart Phone").fullText().buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(10l).name("Mark's Mobile Shop").mainProducts("Smart Phone, Stupid Phone").fullText().buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(11l).name("Charlice Mobile Shop").mainProducts("Apple Phone, Old Phone").fullText().buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(12l).name("Charlice Mobile Shop").mainProducts("Apple Phone").fullText().buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(13l).name("Charlice Mobile Shop").mainProducts("Apple Phone").fullText().buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(14l).name("Oppo Authorized Shop Owned by Joe in Wuhan").mainProducts("Apple Phone").fullText().buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(15l).name("Charlice Fruit Shop").mainProducts("Pear, Watermelon").fullText().buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(16l).name("Jane's Mobile Shop").mainProducts("Smart Phone").fullText().buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(17l).name("Charlise Shop").mainProducts("Smart Phone").fullText().buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(18l).name("Authorized Owned by Joe Meizu Shop").mainProducts("Smart Phone").fullText().buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(2l).name("Meizu Authorized Shop Located in Wuhan Optic Valley Software Park").mainProducts("Smart Phone").fullText().buildIndex());
		
		for(int i=18; i<30; i++) {
			elasticsearchTemplate.index(new StoreDocBuilder(Long.valueOf(i).longValue()).name("Charlise Shop").mainProducts("Apple Phone").fullText().buildIndex());
		}
	}

	private void createProductDocs(){
		List<ProductDoc> list = new ArrayList<ProductDoc>();
		list.add(new ProductDoc(0L, "Fake", "Falso", "Iphone! Iphone! Smart Phone", "GENERAL", Boolean.FALSE, DateUtils.addDays(new Date(), 10)));
		list.add(new ProductDoc(1L, "Mac Pro","Mac Pro", "Mac Pro  is made by China, it's a Smart Phone", "SMARTPHONE", Boolean.TRUE, DateUtils.addDays(new Date(), 3)));
		list.add(new ProductDoc(2L, "Huawei","Huwwei", "Made by China", "SMARTPHONE", Boolean.FALSE, DateUtils.addDays(new Date(), 100)));
		list.add(new ProductDoc(3L, "Huawei Max3","Huawei Max3", "Huawei is designed / made by China, it's a Smart Phone", "SMARTPHONE", Boolean.FALSE, DateUtils.addDays(new Date(), -10)));
		list.add(new ProductDoc(4L, "Nokia", "Nokia","Nokia better than Iphone", "GENERAL", Boolean.FALSE, DateUtils.addDays(new Date(), 10)));
		list.add(new ProductDoc(5L, "Iphone X","Iphone X", "Iphone X is made by China", "SMARTPHONE", Boolean.TRUE, DateUtils.addDays(new Date(), -20)));
		list.add(new ProductDoc(6L, "Nokia is Note","Nokia es Note", "Nokia is Smart Phone an very old brand and famous", "SMARTPHONE", Boolean.FALSE, DateUtils.addDays(new Date(), -40)));
		list.add(new ProductDoc(7L, "Nokia N90","Nokia N90", "Nokia N 90 is made by China", "GENERAL", Boolean.FALSE, DateUtils.addDays(new Date(), -10)));
		list.add(new ProductDoc(8L, "Vivo Z1","Vivo Z1", "Vivo is made by China, It's very popular in China.", "GENERAL", Boolean.FALSE, DateUtils.addDays(new Date(), 60)));
		list.add(new ProductDoc(9L, "Huawei Max3","Huawei Max3", "is designed / made by China", "SMARTPHONE", Boolean.FALSE, DateUtils.addDays(new Date(), 1)));
		productDocRepository.save(list);
	}
}
