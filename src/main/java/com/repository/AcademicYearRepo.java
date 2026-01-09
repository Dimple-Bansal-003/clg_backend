package com.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.AcademicYearEntity;

public interface AcademicYearRepo extends JpaRepository<AcademicYearEntity, Long> {
    Optional<AcademicYearEntity> findByYearLabel(String yearLabel);
}
