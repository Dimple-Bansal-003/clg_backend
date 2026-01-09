package com.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.entity.RoleEntity;
import com.entity.SubjectEntity;
import com.entity.UserEntity;
import com.repository.RoleRepo;
import com.repository.SubjectRepo;
import com.repository.UserRepo;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            RoleRepo roleRepo,
            UserRepo userRepo,
            PasswordEncoder passwordEncoder,
            SubjectRepo subjectRepo
            
    ) {
        return args -> {

            // ===== ROLES =====
            RoleEntity adminRole = roleRepo.findByRoleName("ADMIN")
                    .orElseGet(() -> roleRepo.save(new RoleEntity(null, "ADMIN")));

            RoleEntity facultyRole = roleRepo.findByRoleName("FACULTY")
                    .orElseGet(() -> roleRepo.save(new RoleEntity(null, "FACULTY")));

            RoleEntity staffRole = roleRepo.findByRoleName("STAFF")
                    .orElseGet(() -> roleRepo.save(new RoleEntity(null, "STAFF")));

            RoleEntity studentRole = roleRepo.findByRoleName("STUDENT")
            	    .orElseGet(() -> roleRepo.save(new RoleEntity(null, "STUDENT")));
           
            // ===== SUBJECTS (Marks ke liye) =====
            if (subjectRepo.findByName("Mathematics").isEmpty()) {
                subjectRepo.save(new SubjectEntity(null, "Mathematics", 100, 40));
            }
            if (subjectRepo.findByName("Physics").isEmpty()) {
                subjectRepo.save(new SubjectEntity(null, "Physics", 50, 20));
            }
            if (subjectRepo.findByName("Chemistry").isEmpty()) {
                subjectRepo.save(new SubjectEntity(null, "Chemistry", 50, 20));
            }
            if (subjectRepo.findByName("Computer Science").isEmpty()) {
                subjectRepo.save(new SubjectEntity(null, "Computer Science", 25, 10));
            }

            
            // ===== USERS =====
            if (userRepo.findByUsername("Admin").isEmpty()) {
                userRepo.save(new UserEntity(
                        null,
                        "Admin",
                        passwordEncoder.encode("admin123"),
                        true,
                        "admin@college.com",
                        adminRole
                ));
            }

            if (userRepo.findByUsername("Faculty").isEmpty()) {
                userRepo.save(new UserEntity(
                        null,
                        "Faculty",
                        passwordEncoder.encode("faculty456"),
                        true,
                        "faculty@college.com",
                        facultyRole
                ));
            }

            if (userRepo.findByUsername("Staff").isEmpty()) {
                userRepo.save(new UserEntity(
                        null,
                        "Staff",
                        passwordEncoder.encode("staff789"),
                        true,
                        "staff@college.com",
                        staffRole
                ));
            }

            System.out.println("✅ Default users & roles initialized");
        };
    }
}
