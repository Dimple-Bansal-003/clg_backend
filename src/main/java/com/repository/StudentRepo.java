package com.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entity.StudentEntity;

@Repository
public interface StudentRepo extends JpaRepository<StudentEntity, Long >{

	Optional<StudentEntity> findByRollNoAndEnrollmentYear(String rollNo, int year);
	
}
