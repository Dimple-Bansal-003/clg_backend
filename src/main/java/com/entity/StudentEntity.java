package com.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "students")  // Your actual table
@Data 
@AllArgsConstructor
@NoArgsConstructor
public class StudentEntity 
{
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="student_id")
	Long student_id;
	
	 @Column(name = "student_name")
	String studentName;
	
	  @Column(name = "student_email")
	String email;
	  
	  @Column(name = "phone", length = 15)
	  String phone;
	  
	   @Column(name = "rollNo", unique = true)
	  String rollNo;
	  
	  Integer enrollmentYear;
	  
	  String division;
	  
	  @OneToOne
	  @JoinColumn(name="user_id")
	  UserEntity user;
	 
}
