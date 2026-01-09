package com.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentExcelRow {
    private String name;
    private String rollNo;
    private String division;
}