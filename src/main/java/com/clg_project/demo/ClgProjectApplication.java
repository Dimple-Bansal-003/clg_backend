package com.clg_project.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com")
@EnableJpaRepositories(basePackages = "com.repository")
@EntityScan(basePackages = "com.entity")
public class ClgProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClgProjectApplication.class, args);
	}

}
