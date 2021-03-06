package com.joe.springdataelasticsearch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.joe.springdataelasticsearch.document.ProductDoc;
import com.joe.springdataelasticsearch.domain.DocumentPage;
import com.joe.springdataelasticsearch.service.ProductDocService;

@RestController
@RequestMapping("/products")
public class ProductDocController {
	@Autowired
	private ProductDocService productDocService;

	@GetMapping
	public List<ProductDoc> search(String keyword, @RequestParam(required=false) Boolean isSelfRun,
			@RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "10") Integer pageSize) {
		return productDocService.search(keyword, isSelfRun, new PageRequest(pageNumber, pageSize)).getContent();
	}
	@GetMapping("/function_search")
	public List<ProductDoc> functionScoreSearch(String keyword, @RequestParam(required=false) Boolean isSelfRun,
			@RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "10") Integer pageSize) {
		 Page<ProductDoc> page = productDocService.functionScoreSearch(keyword, isSelfRun, new PageRequest(pageNumber, pageSize));
		 return page.getContent();
	}
	
	@GetMapping("/aggregation")
	public DocumentPage<ProductDoc> aggregationSearch(String keyword, @RequestParam(required=false) Boolean isSelfRun,
			@RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "10") Integer pageSize) {
		return productDocService.aggregationSearch(keyword, isSelfRun, new PageRequest(pageNumber, pageSize));
	}
	
	@GetMapping("/suggestions")
	public List<String> suggestions(String keyword) {
		return productDocService.suggest(keyword, 10);
	}
}
