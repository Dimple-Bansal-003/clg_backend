package com.entity;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "student_marks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentMarksEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "student_academic_id")
    private StudentAcademicEntity studentAcademic;
    
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private SubjectEntity subject;
    
    private Double obtainedMarks;
    private Double totalMarks;  // Computed
    private Double percentage;  // Computed
}

