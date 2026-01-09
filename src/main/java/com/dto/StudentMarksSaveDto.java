package com.dto;

import lombok.Data;

@Data
public class StudentMarksSaveDto {

	private Long studentAcademicId;
    private Long subjectId;
    private Double obtainedMarks;
}
