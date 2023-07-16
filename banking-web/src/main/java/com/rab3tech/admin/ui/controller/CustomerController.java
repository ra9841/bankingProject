package com.rab3tech.admin.ui.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.rab3tech.customer.service.CustomerService;
import com.rab3tech.vo.CustomerUpdateVO;
import com.rab3tech.vo.CustomerVO;
import com.rab3tech.vo.LoanVo;

@Controller
@RequestMapping("/admin")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/customers")
	public String showCustomer(Model model) {
	   List<CustomerVO> customerVOs=customerService.findCustomers();
	   model.addAttribute("customerVOs", customerVOs);
	   return "admin/customers";
	}
		
	
	@GetMapping("/customers/photo")
	public void findCustomerPhoto(@RequestParam int cid,HttpServletResponse response) throws IOException {
	   byte[] photo=customerService.findPhotoByid(cid);
	   response.setContentType("image/png");
	   ServletOutputStream outputStream=response.getOutputStream();
	   if(photo!=null) {
		   outputStream.write(photo);
	   }else {
		   outputStream.write(new byte[] {});
	   }
	   outputStream.flush();
	   outputStream.close();
	}
	
	/*@PostMapping("/updateCustomer")
	public String updateCustomer(@RequestParam("cid") int cid,@RequestParam String name,String mobile,MultipartFile photo,Model model) throws IOException {
		CustomerVO customerVO=new CustomerVO();
		customerVO.setId(cid);
		customerVO.setName(name);
		customerVO.setMobile(mobile);
		customerVO.setImage(photo.getBytes());
		customerService.updateProfile(customerVO);
		//redirect to show all the records of the current customer in the database!
	   return "admin/customers";
	}*/
	
	@PostMapping("/updateCustomer")
	public String updateCustomer(@ModelAttribute CustomerUpdateVO customerUpdateVO,Model model) throws IOException {
		customerService.updateProfile(customerUpdateVO);
		//redirect to show all the records of the current customer in the database!
	   return "redirect:/admin/customers";
	}
	
	/*
	 * @GetMapping("/filter/customers") public String
	 * showCustomerByFilter(@RequestParam String soption,Model model) {
	 * List<CustomerVO>customerVos=customerService.findCustomers();
	 * List<CustomerVO>filterList=new ArrayList<>();
	 * 
	 * if("Customer".equals(soption)) { for(CustomerVO customerVo:customerVos) {
	 * if("Customer".equalsIgnoreCase(customerVo.getRole())) {
	 * filterList.add(customerVo); } } } else
	 * if("Employee".equalsIgnoreCase(soption)) { for(CustomerVO
	 * customerVo:customerVos) {
	 * if("Employee".equalsIgnoreCase(customerVo.getRole())) {
	 * filterList.add(customerVo); } } } else { filterList.addAll(customerVos); }
	 * model.addAttribute("customerVOs",filterList); return "admin/dashboard"; }
	 */
	
	
	
	@GetMapping("/filter/customers")
	public String showCustomerByAnotherFilters(@RequestParam String soption,Model model) {
		List<CustomerVO>customerVos=customerService.findCustomers(soption);
		model.addAttribute("customerVOs",customerVos);
		model.addAttribute("option", soption);
		return "admin/dashboard";
	}
	//for ascending sorting
	@GetMapping("/sort/names/{option}")///{option}
	public String showCustomerNameSort(@PathVariable String option,Model model) {//@PathVariable String option,
		List<CustomerVO>customerVos=customerService.sortCustomers(option);//option
		model.addAttribute("customerVOs",customerVos);
		return "admin/dashboard";
	}
	//for descending sorting
	@GetMapping("/sort/namesDes/{option}")///{option}
	public String showCustomerNameSortDesc(@PathVariable String option,Model model) {//@PathVariable String option,
		List<CustomerVO>customerVos=customerService.sortCustomersDec(option);//option
		model.addAttribute("customerVOs",customerVos);
		return "admin/dashboard";
	}
	
	
	@GetMapping("/loanTypes")
	public String newLoanType(Model model) {
		List<LoanVo>loanVoLis=customerService.getAllLoanInfo();
		
		model.addAttribute("loanVos",loanVoLis);
		
		LoanVo loanVos=new LoanVo();//blank
		model.addAttribute("loanVo", loanVos);
		return "admin/loanType";  //loantype.html
	}
	
	
	@PostMapping("/loanTypes")
	public String registrationLoan(@Valid @ModelAttribute LoanVo loanVo,BindingResult result,MultipartFile file,Model model) throws IOException {
		if (result.hasErrors()) {
			List<LoanVo>loanVoLis=customerService.getAllLoanInfo();
			model.addAttribute("loanVos",loanVoLis);
			
			return "admin/loanType";
		}
		//for image uploading to database
		byte[] bphoto=file.getBytes();
		loanVo.setTphoto(bphoto);
		
		LoanVo loanvo=customerService.registerCustomerLoanInfo(loanVo);
     	model.addAttribute("loanVos",loanvo);
		
		LoanVo loanVoss=new LoanVo();//blank
		model.addAttribute("loanVo", loanVoss);
		return "admin/loanType";
	}
	
	/*
	 * @GetMapping("/loaned") public String getLoanInfo(Model model) {
	 * List<LoanVo>loanVo=customerService.getAllLoanInfo();
	 * model.addAttribute("loanVo",loanVo); return "admin/loanType"; }
	 */
	
	
	//Special code to render images by URL
			@GetMapping("/loantype/photo")
			public void findCustomerPhotoPic(@RequestParam int id,HttpServletResponse response) throws IOException {
			   byte[] photo=customerService.findPicById(id);
			   response.setContentType("image/jpg");
			   ServletOutputStream outputStream=response.getOutputStream();
			   if(photo!=null) {
				   //writing photo inside response body 
				   outputStream.write(photo);
			   }else {
				   outputStream.write(new byte[] {});
			   }
			   outputStream.flush();
			   outputStream.close();
			}

}
