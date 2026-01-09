package com.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "users")  // Your actual table
@Data @NoArgsConstructor @AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    
    @NotBlank
    @Size(min=3,max=50)
    @Column(unique=true)
    private String username;
    
    @NotBlank
    @Size(min=8,max=100)
    @Column(name = "password")
    private String password;
    
    @NotNull
    @Column(name = "is_active", columnDefinition = "BIT default 1")
    private Boolean isActive=true;
   
    @Email
    @Size(max=100) 
    private String email;
    
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private RoleEntity role;
    
    
}
