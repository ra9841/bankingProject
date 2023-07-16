package com.rab3tech.admin.ui.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rab3tech.customer.service.CustomerService;
import com.rab3tech.vo.CustomerLocationVo;

@RestController
@RequestMapping("/v3")
public class BranchApiController {
	@Autowired
	CustomerService customerService;
	
	
	
	  @GetMapping("/location")
	    public List<CustomerLocationVo> selectingLocation() {
	    	List<CustomerLocationVo> customerLocationVo=customerService.askingCustomerLocation();
	    	
	    	return customerLocationVo;
	    }

}
