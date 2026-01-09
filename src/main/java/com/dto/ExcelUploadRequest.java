package com.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ExcelUploadRequest {

	private MultipartFile file;
	private int enrollmentYear;
	private String academicYear;
	private int semester;
	
}
