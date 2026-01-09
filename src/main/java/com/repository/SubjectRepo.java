package com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entity.SubjectEntity;

@Repository
public interface SubjectRepo extends JpaRepository<SubjectEntity, Long> {
List<SubjectEntity> findByNameIn(List<String> names);
    
   
    Optional<SubjectEntity> findByName(String name);
}