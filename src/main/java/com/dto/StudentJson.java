package com.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StudentJson {

	@NotBlank
	@Size(max=100)
	private String name;
	
	@NotBlank
	@Size(max=20)
	private String rollNo;
	
	@Size(max=100)
	private String email;
	
	@Size(max=15)
	private String phone;
	
	@NotBlank
	@Size(min=3,max=50)
	private String username;
	
	@NotBlank
	@Size(min=8,max=20)
	private String password;
	
	@Size(max=10)
	private String division;
	
}
