package com.chatop.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.dto.AuthDTO;
import com.chatop.dto.UserDTO;
import com.chatop.service.AuthService;
import com.chatop.service.JWTService;
import com.chatop.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;
	private final JWTService jwtService;
	private final UserService userService;

	@Autowired
	public AuthController(AuthService authService, JWTService jwtService, UserService userService) {
		this.authService = authService;
		this.jwtService = jwtService;
		this.userService = userService;
	}

	@PostMapping("/register")
	public ResponseEntity<String> register(@Valid @RequestBody UserDTO user, BindingResult result) {
		if (result.hasErrors()) {
			String errorMessages = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
			return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
		} else {
			authService.register(user);
			return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody AuthDTO user) {
		boolean isAuthenticated = authService.authenticate(user.getEmail(), user.getPassword());

		if (isAuthenticated) {
			Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
			String token = jwtService.generateToken(authentication);
			return ResponseEntity.ok(token);
		} else {
			return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
		}
	}
	
	@GetMapping("/me")
	public ResponseEntity<UserDTO> getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        Optional<UserDTO> optionalUserDTO = userService.findByEmail(email);
        if (optionalUserDTO.isPresent()) {
            UserDTO userDTO = optionalUserDTO.get();
            return ResponseEntity.ok(userDTO);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
