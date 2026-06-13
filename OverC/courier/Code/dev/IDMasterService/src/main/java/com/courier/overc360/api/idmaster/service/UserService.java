package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.user.User;
import com.courier.overc360.api.idmaster.primary.repository.UserRepository;
import com.courier.overc360.api.idmaster.primary.util.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	private PasswordEncoder passwordEncoder = new PasswordEncoder();
	
	public User validateUser (String username, String loginPassword) {
		User user = userRepository.findByUsername(username).orElse(null);
		if (user == null) {
    		throw new BadRequestException("Invalid Username : " + username);
    	}
		
		boolean isSuccess = passwordEncoder.matches(loginPassword, user.getPassword());
		if (!isSuccess) {
			throw new BadRequestException("Password is wrong. Please enter correct password.");
		}
		
		return user;
	}
}
