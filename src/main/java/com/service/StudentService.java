package com.service;
import com.dto.ExcelUploadRequest;
import com.dto.StudentBulkDto;
import com.dto.StudentJson;
import com.entity.*;
import com.repository.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class StudentService {
    private final UserRepo userRepo;
    private final StudentRepo studentRepo;
    private final AcademicYearRepo ayRepo;
    private final SemesterRepo semRepo;
    private final StudentAcademicRepo saRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;  // For JSON plaintext

    private final EmailService emailService;
    
    public String uploadExcelStudents(ExcelUploadRequest re) {
        // Your existing Excel logic here (parse, generate username/password server-side)
        // ... (copy from your paste.txt)
        return "Excel upload success";
    }

    public String saveBulkStudents(@Valid StudentBulkDto dto) {
        AcademicYearEntity ay = ayRepo.findByYearLabel(dto.getAcademicYear())
            .orElseGet(() -> ayRepo.save(new AcademicYearEntity(null, dto.getAcademicYear())));

        RoleEntity studentRole = roleRepo.findByRoleName("STUDENT")
            .orElseGet(() -> roleRepo.save(new RoleEntity(null, "STUDENT")));

        
        SemesterEntity sem = semRepo.findBySemesterNoAndAcademicYearId(dto
        		.getSemester(), ay.getId())
                .orElseGet(() -> {
                    SemesterEntity newSem = new SemesterEntity();
                    newSem.setSemesterNo(dto.getSemester());
                    newSem.setAcademicYear(ay);
                    return semRepo.save(newSem);
                });
        
        int count = 0;
        int emailed = 0;
        
        for (StudentJson sj : dto.getStudents()) {
            // Skip if rollNo already exists in this enrollment year
            if (studentRepo.findByRollNoAndEnrollmentYear(sj
            		.getRollNo(), dto.getEnrollmentYear()).isPresent()) {
                continue;
            }

            // Check username unique
            if (userRepo.findByUsername(sj.getUsername()).isPresent()) {
                continue; // Skip duplicate username
            }

            try {
            // Create user (hash frontend password)
            UserEntity user = new UserEntity();
            user.setUsername(sj.getUsername());
            user.setPassword(passwordEncoder.encode(sj.getPassword())); // Hash plain password
            user.setEmail(sj.getEmail());  
            user.setRole(studentRole);
            user.setIsActive(true);
            user = userRepo.save(user);

            // Create student profile
            StudentEntity student = new StudentEntity();
            student.setStudentName(sj.getName());
            student.setRollNo(sj.getRollNo());
            student.setEmail(sj.getEmail());
            student.setPhone(sj.getPhone() != null ? sj.getPhone() : "");
            student.setEnrollmentYear(dto.getEnrollmentYear());
            student.setDivision(dto.getDivision() != null ? dto.getDivision() : "A");
            student.setUser(user);
            studentRepo.save(student);

            // Enroll student in specific year/semester/division
            StudentAcademicEntity sa = new StudentAcademicEntity();
            sa.setStudent(student);
            sa.setAcademicYear(ay);
            sa.setSemester(sem);
            sa.setDivision(dto.getDivision() != null ? dto.getDivision() : "A");
            saRepo.save(sa);

            
            emailService.sendCredentials(
                    sj.getEmail(),      // ✅ Excel email
                    sj.getUsername(),
                    sj.getPassword(),   // Plain password
                    sj.getRollNo(),
                    sj.getName()
                );
            
            count++;
            emailed++;
        }
            catch(Exception e) {
                System.out.println("❌ Failed " + sj.getName() + ": " + e.getMessage());
            }
        }
        return String.format("✅ %d/%d saved | %d emails sent!\n%s Sem %d Div %s", 
                count, dto.getStudents().size(), emailed, dto.getAcademicYear(),
                dto.getSemester(), dto.getDivision());
    }
    public List<Map<String, Object>> getStudentsByAcademicYearSemesterDivision(String academicYear, int semester, String division) {
        List<StudentEntity> students = saRepo.findStudentsByAcademicYearSemesterDivision(academicYear, semester, division);
        List<Map<String, Object>> result = new ArrayList<>();
        for (StudentEntity student : students) {
            result.add(Map.of(
                    "id", student.getStudent_id(),
                    "rollno", student.getRollNo(),
                    "name", student.getStudentName()
            ));
        }
        return result;
    }	
}

