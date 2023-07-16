package com.rab3tech.employee.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.rab3tech.common.service.BranchService;
import com.rab3tech.test.TestUtil;
import com.rab3tech.vo.BranchVO;

public class BranchAPIControllerTest {
	
	private MockMvc mockMvc;
	
	@Mock
	private BranchService branchService;
	
	@InjectMocks
	private BranchAPIController branchAPIController;
	
	
	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
		//Initializing mock mvc to hit rest api
		mockMvc = MockMvcBuilders.standaloneSetup(branchAPIController)
				// .addFilters(new CorsFilter())
				.build();
	}
	
	@GetMapping("/branches/{id}")
	public BranchVO showBranch(@PathVariable int id){
		return branchService.findById(id);
	}

	
	@Test
	public void testShowBranch() throws IOException, Exception {
		BranchVO branchVO = new BranchVO();
		branchVO.setId(100);
		branchVO.setAddress("JA82");
		branchVO.setCity("Fremont");
		branchVO.setCode("O92192");
		branchVO.setIfsc("ICICI91828");

		when(branchService.findById(100)).thenReturn(branchVO);

		mockMvc.perform(MockMvcRequestBuilders.get("/v3/branches/100")
				// What format of data we are expecting from server
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				// output we are validating here
				.andExpect(jsonPath("$.id").exists()).andExpect(jsonPath("$.address").exists())
				.andExpect(jsonPath("$.city").exists()).andExpect(jsonPath("$.code").exists())
				.andExpect(jsonPath("$.ifsc").exists()).andExpect(jsonPath("$.id").value("100"))
				.andExpect(jsonPath("$.address").value("JA82")).andExpect(jsonPath("$.city").value("Fremont"))
				.andExpect(jsonPath("$.code").value("O92192")).andExpect(jsonPath("$.ifsc").value("ICICI91828"))
				.andDo(print());

		verify(branchService, times(1)).findById(100);
		verifyNoMoreInteractions(branchService);

	}
	
	
	@Test
	public void testCreateNewBranch() throws IOException, Exception{
		
		BranchVO branchVO=new BranchVO();
		branchVO.setAddress("JA82");
		branchVO.setCity("Fremont");
		branchVO.setCode("O92192");
		branchVO.setIfsc("ICICI91828");
		
		BranchVO branch=new BranchVO();
		branch.setId(9000);
		branch.setAddress("JA823");
		branch.setCity("HAYA");
		branch.setCode("O93242");
		branch.setIfsc("ICICI9234");
		
		when(branchService.save(any(BranchVO.class)))
		.thenReturn(branch);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/v3/branches")
			     //what format of data we are sending here
	 	        .contentType(MediaType.APPLICATION_JSON)
	 	         //setting  json data into request body
	 	        .content(TestUtil.convertObjectToJsonBytes(branchVO))
	 	        //What format of data we are expecting from server
	 			.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
	             //output we are validating here 
	 			.andExpect(jsonPath("$.id").exists())
	 			.andExpect(jsonPath("$.address").exists())
	 			.andExpect(jsonPath("$.city").exists())
	 			.andExpect(jsonPath("$.code").exists())
	 			.andExpect(jsonPath("$.ifsc").exists())
	 			.andExpect(jsonPath("$.id").value("9000"))
	 			.andExpect(jsonPath("$.address").value("JA823"))
	 			.andExpect(jsonPath("$.city").value("HAYA"))
	 			.andExpect(jsonPath("$.code").value("O93242"))
	 			.andExpect(jsonPath("$.ifsc").value("ICICI9234"))
	 			.andDo(print());
		
	}
	
	@Test
	public void testDeleteBranch() throws Exception {
		
		//mocking the method which does return anything
		doNothing().when(branchService).deleteById(isA(Integer.class));
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/v3/branches/90")
	 	        //What format of data we are expecting from server
	 			.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
	             //output we are validating here 
			     .andExpect(jsonPath("$.code").exists())
			     .andExpect(jsonPath("$.message").exists())
		        .andExpect(jsonPath("$.code").value("200"))
		        .andExpect(jsonPath("$.message").value("Branch is deleted successfully"))
	 			.andDo(print());
		
		verify(branchService, times(1)).deleteById(isA(Integer.class));
		verifyNoMoreInteractions(branchService);
		
	}
	

	@Test
	public void testAllBranches() throws Exception {
		//Creating dummy data
		List<BranchVO> branchVOs=new ArrayList<BranchVO>();
		
		BranchVO branchVO=new BranchVO();
		branchVO.setAddress("JA82");
		branchVO.setCity("Fremont");
		branchVO.setCode("O92192");
		branchVO.setIfsc("ICICI91828");
		
		BranchVO branch=new BranchVO();
		branch.setAddress("JA823");
		branch.setCity("HAYA");
		branch.setCode("O93242");
		branch.setIfsc("ICICI9234");
		branchVOs.add(branchVO);
		branchVOs.add(branch);
		
		when(branchService.findAll())
		.thenReturn(branchVOs);
		
		//Now I will call my rest API
		
		 mockMvc.perform(MockMvcRequestBuilders.get("/v3/branches")
	 	        //What format of data we are expecting from server
	 			.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
	             //output we are validating here 
		        .andExpect(jsonPath("$[0].address").value("JA82"))
		        .andExpect(jsonPath("$[0].city").value("Fremont"))
		        .andExpect(jsonPath("$[0].code").value("O92192"))
		        .andExpect(jsonPath("$[0].ifsc").value("ICICI91828"))
		        .andExpect(jsonPath("$[1].address").value("JA823"))
		        .andExpect(jsonPath("$[1].city").value("HAYA"))
		        .andExpect(jsonPath("$[1].code").value("O93242"))
		        .andExpect(jsonPath("$[1].ifsc").value("ICICI9234"))
	 			.andDo(print());
	}

}
