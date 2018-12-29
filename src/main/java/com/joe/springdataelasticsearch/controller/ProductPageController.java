package com.joe.springdataelasticsearch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.joe.springdataelasticsearch.document.ProductDoc;
import com.joe.springdataelasticsearch.domain.DocumentPage;
import com.joe.springdataelasticsearch.service.ProductDocService;

@Controller
@RequestMapping("/page")
public class ProductPageController {
	@Autowired
	private ProductDocService productDocService;

	@GetMapping("/products")
    public String searchProducts(Model model,String keyword) {
		DocumentPage<ProductDoc> page =  productDocService.aggregationSearch(keyword, false, new PageRequest(0, 10));
        model.addAttribute("page", page);
        return "/product/products";
    }
}
