package com.joe.springdataelasticsearch.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.joe.springdataelasticsearch.document.StoreDoc;
import com.joe.springdataelasticsearch.service.StoreDocService;
import com.joe.springdataelasticsearch.repository.StoreDocRepository;

@Controller
@RequestMapping("/page")
public class StorePageController {
	@Autowired
	private StoreDocService storeDocService;

	@Autowired
	private StoreDocRepository storeDocRepository;
	
	@GetMapping("/store-names")
    public String searchStoreNames(Model model,String keyword) {
		Page<StoreDoc> page =  storeDocService.searchInName(keyword, new PageRequest(0, 20));
		model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        return "/store/storeNames";
    }
	
	@GetMapping("/store-names-closer-better")
    public String searchStoreNamesCloserBetter(Model model,String keyword) {
		Page<StoreDoc> page = null;
		if (StringUtils.isEmpty(keyword)) {
			page = storeDocService.findAll(new PageRequest(0, 20));
		}else {
			page = storeDocService.searchInNameCloserBetter(keyword, new PageRequest(0, 20));			
		}
		model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        return "/store/storeNamesCloserBetter";
    }
	
	
	@GetMapping("/store-names-strict")
    public String searchStoreNamesStrict(Model model,String keyword) {
		Page<StoreDoc> page =  storeDocRepository.findByName(keyword, new PageRequest(0, 20));
		model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        return "/store/storeNamesStrict";
    }
	
	@GetMapping("/stores")
    public String searchStores(Model model,String keyword) {
		Page<StoreDoc> page =  storeDocService.search(keyword, new PageRequest(0, 20));
		model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        return "/store/stores";
    }
	@GetMapping("/stores-closer-better")
    public String searchStoresCloserBetter(Model model,String keyword) {
		Page<StoreDoc> page = null;
		if (StringUtils.isEmpty(keyword)) {
			page = storeDocService.findAll(new PageRequest(0, 20));
		}else {
			page =  storeDocService.searchCloserBetter( keyword, new PageRequest(0, 20));
		}
		model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        return "/store/storesCloserBetter";
    }
	
	@GetMapping("/stores-cross-fields-search")
    public String searchStoresCrossFields(Model model,String keyword) {
		Page<StoreDoc> page =  storeDocService.searchCorssFields(keyword, new PageRequest(0, 20));
		model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        return "/store/stores-cross-fields-search";
    }
	
	@GetMapping("/stores-no-idf")
    public String searchStoresFunctionally(Model model,String keyword) {
		Page<StoreDoc> page =  storeDocService.searchFunctionally(keyword, new PageRequest(0, 20));
		model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        return "/store/stores-no-idf";
    }
	
	@GetMapping("/stores/fuzzy")
    public String searchStoresFuzzily(Model model,String keyword) {
		Page<StoreDoc> page =  storeDocService.searchFuzzily(keyword, new PageRequest(0, 20));
		model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        return "/store/storeFuzzy";
    }
	
	@GetMapping("/stores/randomscoring")
    public String searchStoresRandomly(Model model,String keyword) {
		Page<StoreDoc> page =  storeDocService.searchRandomly(keyword, new PageRequest(0, 5));
		model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        return "/store/randomscoring";
    }
}
