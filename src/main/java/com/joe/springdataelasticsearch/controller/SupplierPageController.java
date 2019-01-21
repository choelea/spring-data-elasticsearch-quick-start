package com.joe.springdataelasticsearch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.joe.springdataelasticsearch.document.SupplierDoc;
import com.joe.springdataelasticsearch.service.SupplierDocService;

@Controller
@RequestMapping("/page")
public class SupplierPageController {
	@Autowired
	private SupplierDocService supplierDocService;
 
	@GetMapping("/suppliers")
    public String searchSuppliers(Model model,String keyword) {
		Page<SupplierDoc> page =  supplierDocService.search(keyword, new PageRequest(0, 20));
		model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        return "/supplier/suppliers";
    }
	 
}
