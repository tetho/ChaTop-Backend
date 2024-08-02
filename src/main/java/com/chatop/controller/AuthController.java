package com.chatop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.dto.LoginRequestDTO;
import com.chatop.dto.RegisterRequestDTO;
import com.chatop.service.AuthService;
import com.chatop.service.JWTService;

@RestController
public class AuthController {

	private final AuthService authService;
	private final JWTService jwtService;

	@Autowired
	public AuthController(AuthService authService, JWTService jwtService) {
		this.authService = authService;
		this.jwtService = jwtService;
	}

	@PostMapping("/auth/register")
	public ResponseEntity<String> register(@RequestBody RegisterRequestDTO registerRequest) {
		authService.register(registerRequest);
		return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
	}

	@PostMapping("/auth/login")
	public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequest) {
		boolean isAuthenticated = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());

		if (isAuthenticated) {
			String token = jwtService.generateToken(loginRequest.getUsername());
			return ResponseEntity.ok(token);
		} else {
			return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
		}
	}
}
