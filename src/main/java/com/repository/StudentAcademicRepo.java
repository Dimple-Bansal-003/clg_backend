package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.entity.StudentAcademicEntity;
import com.entity.StudentEntity;


@Repository
public interface StudentAcademicRepo extends JpaRepository<StudentAcademicEntity, Long> {
	@Query("SELECT sa.student FROM StudentAcademicEntity sa " +  // sa.student ✅
		       "JOIN sa.academicYear ay " +
		       "JOIN sa.semester sem " +
		       "WHERE ay.yearLabel = :academicYear " +
		       "AND sem.semesterNo = :semester " +
		       "AND (:division IS NULL OR sa.division = :division)")
		List<StudentEntity> findStudentsByAcademicYearSemesterDivision(
		        @Param("academicYear") String academicYear,
		        @Param("semester") int semester,
		        @Param("division") String division);

}
