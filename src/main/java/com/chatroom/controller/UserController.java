package com.chatroom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatroom.Repo.UserRepo;
import com.chatroom.model.User;
import com.chatroom.model.UserDTO;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserRepo userRepo;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO){
		String username = userDTO.getUsername();
		String password = userDTO.getPassword();
		if(userRepo.findByUsername(username)!=null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
		}
		System.out.println("IN Register Block");
		String hashedPassword = passwordEncoder.encode(password);
		
		User newUser = new User();
		newUser.setUsername(username);
		newUser.setPassword(hashedPassword);
		userRepo.save(newUser);
		
		return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
	}

	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody UserDTO userDTO){
		String username = userDTO.getUsername();
		String password = userDTO.getPassword();
		User user = userRepo.findByUsername(username);
		
		if(user == null || !passwordEncoder.matches(password, user.getPassword())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
		}
		
		return ResponseEntity.ok("Login successful");
	}
}
