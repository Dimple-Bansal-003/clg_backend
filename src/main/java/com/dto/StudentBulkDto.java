package com.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Valid
@Data
public class StudentBulkDto {

	@Min(2000)
	private int enrollmentYear;
	
	@NotBlank
	@Size(max=20)
	private String academicYear;
	
	@Min(1)
	@Max(8)
	private int semester;
	
	@NotEmpty
	private List<@Valid StudentJson> students;
	
}
