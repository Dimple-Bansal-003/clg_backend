package com.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dto.ExcelUploadRequest;
import com.dto.StudentBulkDto;
import com.service.EmailService;
import com.service.StudentService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins="*")
public class AdminController {

private final StudentService studentService;

private final EmailService emailService;
    
    @PostMapping("/upload-students")
    public ResponseEntity<String> uploadStudents(
    	@RequestPart("file") MultipartFile file,
    	@RequestPart("re") @Valid ExcelUploadRequest re){
    		
    	return ResponseEntity.ok(studentService.uploadExcelStudents(re));
    }
    
  
    @PostMapping("/students/bulk")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> bulkStudents(@RequestBody @Valid StudentBulkDto dto){
    	return ResponseEntity.ok(studentService.saveBulkStudents(dto));
    }
    
    @GetMapping("/students")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Map<String, Object>>> getStudentsByFilters(
            @RequestParam String academicYear,
            @RequestParam int semester,
            @RequestParam(required = false) String division) {
        List<Map<String, Object>> students = studentService.getStudentsByAcademicYearSemesterDivision(academicYear, semester, division);
        return ResponseEntity.ok(students);
    }
    
    @PostMapping("/test-email")
    public ResponseEntity<String> testEmail(HttpServletRequest request) {
        String to = request.getParameter("to");
        System.out.println("🔍 DEBUG to param: '" + to + "'");
        System.out.println("🔍 Content-Type: " + request.getContentType());
        
        if (to == null || to.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("❌ to parameter missing: " + to);
        }
        
        emailService.sendCredentials(to.trim(), "testuser", "Test123!", "TEST-001", "Test Student");
        return ResponseEntity.ok("✅ Email queued to: " + to);
    }
}

