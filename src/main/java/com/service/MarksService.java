package com.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.dto.MarksSubjectDto;
import com.dto.StudentMarksDto;
import com.dto.StudentMarksSaveDto;
import com.entity.StudentAcademicEntity;
import com.entity.StudentEntity;
import com.entity.StudentMarksEntity;
import com.entity.SubjectEntity;
import com.repository.StudentAcademicRepo;
import com.repository.StudentMarksRepo;
import com.repository.SubjectRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MarksService {
    private final StudentAcademicRepo saRepo;
    private final SubjectRepo subjectRepo;
    private final StudentMarksRepo marksRepo;

    public List<StudentMarksDto> getStudentsForMarks(String academicYear,
    		Integer semester, String division) {
        // Fetch StudentAcademic records matching filters
        List<StudentAcademicEntity> saList = saRepo.findByAcademicYear_YearLabelAndSemester_SemesterNoAndDivision(
            academicYear, semester, division);
        
        List<StudentMarksDto> result = new ArrayList<>();
        List<SubjectEntity> subjects = subjectRepo.findAll();
        
        for (StudentAcademicEntity sa : saList) {
            StudentEntity student = sa.getStudent();
            StudentMarksDto dto = new StudentMarksDto();
            dto.setRollNo(student.getRollNo());
            dto.setName(student.getStudentName());
            dto.setStudentAcademicId(sa.getId());
            
            Map<String, MarksSubjectDto> subjectsMap = new HashMap<>();
            for (SubjectEntity sub : subjects) {
                StudentMarksEntity marks = marksRepo.
                		findByStudentAcademicIdAndSubjectId(sa.getId(),
                				sub.getId()).orElse(null);
                MarksSubjectDto subDto = new MarksSubjectDto();
                subDto.setSubjectId(sub.getId());
                subDto.setName(sub.getName());
                subDto.setTotal(sub.getTotalMarks());
                subDto.setPassing(sub.getPassingMarks());
                subDto.setMarks(marks != null ? marks.getObtainedMarks() : 0.0);
                subjectsMap.put(sub.getName(), subDto);
            }
            dto.setSubjects(subjectsMap);
            result.add(dto);
        }
        return result;
    }

    public String saveMarks(List<StudentMarksSaveDto> marksList) {
        for (StudentMarksSaveDto saveDto : marksList) {
            StudentAcademicEntity sa = saRepo.findById(saveDto
            		.getStudentAcademicId()).orElseThrow(() -> new RuntimeException("Student academic not found"));
            SubjectEntity subject = subjectRepo.findById(saveDto.getSubjectId())
            		.orElseThrow(() -> new RuntimeException("Subject not found"));
            
            StudentMarksEntity marks = marksRepo
            		.findByStudentAcademicIdAndSubjectId(sa.getId(), subject.getId())
                .orElse(new StudentMarksEntity());
            marks.setStudentAcademic(sa);
            marks.setSubject(subject);
            marks.setObtainedMarks(saveDto.getObtainedMarks());
            marks.setTotalMarks(subject.getTotalMarks().doubleValue());
            
            // Calculate percentage per subject (or aggregate later)
            marks.setPercentage((marks.getObtainedMarks() / marks.getTotalMarks()) * 100);
            marksRepo.save(marks);
        }
        return "Marks saved successfully";
    }
}