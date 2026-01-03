package com.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")  // Your actual table
@Data @NoArgsConstructor @AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    
    @Column(name = "username")
    private String username;
    
    @Column(name = "password")
    private String password;
    
    @Column(name = "is_active", columnDefinition = "BIT")
    private Boolean isActive;
    
    @Column(name = "email")
    private String email;
    
    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity role;
    
    // Add getter if Lombok issues
    public Boolean getIsActive() { return isActive != null ? isActive : false; }
}
