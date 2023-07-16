package com.rab3tech.vo;

import java.sql.Timestamp;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.rab3tech.validation.annotation.LoanCodeForName;

public class LoanVo {
	private int id;
	//@NotEmpty(message="Write Loan Type??????")
	private String description;
	
	//@NotBlank(message="is required??????????")
	private String loanCode;

//	@Size( min=3,max=20,message="is required!!!!!")
//	@LoanCodeForName(message="start with b" ,Value="b")
	@LoanCodeForName
	//@Pattern(regexp=".+@.+\\.[a-z]+")
	private String name;
	private Timestamp doe;
	private Timestamp dom;
	private MultipartFile file;
	private byte[] tphoto;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Timestamp getDoe() {
		return doe;
	}
	public void setDoe(Timestamp doe) {
		this.doe = doe;
	}
	public Timestamp getDom() {
		return dom;
	}
	public void setDom(Timestamp dom) {
		this.dom = dom;
	}
	
	
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public byte[] getTphoto() {
		return tphoto;
	}
	public void setTphoto(byte[] tphoto) {
		this.tphoto = tphoto;
	}
	@Override
	public String toString() {
		return "LoanVo [id=" + id + ", description=" + description + ", loanCode=" + loanCode + ", name=" + name
				+ ", doe=" + doe + ", dom=" + dom + "]";
	}

	
	
	

}
