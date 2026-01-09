package com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entity.StudentMarksEntity;

@Repository
public interface StudentMarksRepo extends JpaRepository<StudentMarksEntity, Long> {
    List<StudentMarksEntity> findByStudentAcademicId(Long studentAcademicId);
    Optional<StudentMarksEntity> findByStudentAcademicIdAndSubjectId(Long saId, Long subjectId);
}