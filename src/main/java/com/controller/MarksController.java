package com.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dto.StudentMarksDto;
import com.dto.StudentMarksSaveDto;
import com.service.MarksService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/marks")
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:5173")
public class MarksController {

	private final MarksService marksService;
	
	@GetMapping("/students")
    @PreAuthorize("hasRole('FACULTY')")
    public ResponseEntity<List<StudentMarksDto>> getStudents(
            @RequestParam String academicYear,
            @RequestParam Integer semester,
            @RequestParam String division) {
        return ResponseEntity.ok(marksService
        		.getStudentsForMarks(academicYear, semester, division));
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('FACULTY')")
    public ResponseEntity<String> saveMarks(@RequestBody List<StudentMarksSaveDto> marks) {
        return ResponseEntity.ok(marksService.saveMarks(marks));
    }
}
