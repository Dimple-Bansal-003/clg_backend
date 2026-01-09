package com.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.SemesterEntity;

public interface SemesterRepo extends JpaRepository<SemesterEntity, Long> {
    Optional<SemesterEntity> findBySemesterNoAndAcademicYearId(int semNo, Long ayId);
}
