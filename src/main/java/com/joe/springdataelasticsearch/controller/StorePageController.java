package com.joe.springdataelasticsearch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.joe.springdataelasticsearch.document.StoreDoc;
import com.joe.springdataelasticsearch.service.StoreDocService;

@Controller
@RequestMapping("/page")
public class StorePageController {
	@Autowired
	private StoreDocService storeDocService;

	@GetMapping("/store-names")
    public String searchStoreNames(Model model,String keyword) {
		Page<StoreDoc> page =  storeDocService.searchInName(keyword, new PageRequest(0, 10));
		model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        return "/store/storeNames";
    }
	@GetMapping("/stores")
    public String searchStores(Model model,String keyword) {
		Page<StoreDoc> page =  storeDocService.search(keyword, new PageRequest(0, 10));
		model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        return "/store/stores";
    }
}
