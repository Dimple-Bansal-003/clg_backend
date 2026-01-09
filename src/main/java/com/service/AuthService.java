package com.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dto.LoginRequest;
import com.dto.LoginResponse;
import com.entity.UserEntity;
import com.repository.UserRepo;

@Service
public class AuthService {

	private final UserRepo userrepo;
    private final PasswordEncoder passwordencoder;
    
    public AuthService(UserRepo userrepo, PasswordEncoder passwordencoder) {
    	this.userrepo = userrepo;
    	this.passwordencoder = passwordencoder;
       }
    
    public LoginResponse login(LoginRequest request) {
    	
    	UserEntity user = userrepo.findByUsername(request.getUsername())
    			.orElseThrow(() -> new RuntimeException("Username does not exist"));
    	//active check
    	if(!user.getIsActive()) {
    		throw new RuntimeException("Account is inactive");
    	}
    	//password check(encrypted)
    	
    	if(!passwordencoder.matches(request.getPassword(), user.getPassword()))
    	{
    		 throw new RuntimeException("Invalid password");
    	}
    	
    	// success response
    	return new LoginResponse(
    			"Login Successful",
    			user.getRole().getRoleName(),
    			user.getUsername()
    			);
    }
    
}
