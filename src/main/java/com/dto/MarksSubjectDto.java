package com.dto;

import lombok.Data;

@Data
public class MarksSubjectDto {

	private Long subjectId;
    private String name;
    private Integer total;
    private Integer passing;
    private Double marks;
}
