package com.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name="role")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleEntity {
	

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	     Long role_id;

	    @NotBlank
	    @Size(min=3,max=20)
	    @Column(unique=true)
	     String roleName; // ADMIN, FACULTY, STUDENT
	
}
