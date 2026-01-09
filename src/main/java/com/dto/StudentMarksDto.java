package com.dto;

import java.util.Map;

import lombok.Data;

@Data
public class StudentMarksDto {

	private String rollNo;
    private String name;
    private Long studentAcademicId;
    private Map<String, MarksSubjectDto> subjects;
}
