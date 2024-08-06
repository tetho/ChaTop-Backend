package com.chatop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.dto.UserDTO;
import com.chatop.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUser(@PathVariable Integer id) {
		UserDTO userDTO = userService.getUser(id)
				.orElseThrow(() -> new RuntimeException("User not found"));;
		return new ResponseEntity<>(userDTO, HttpStatus.OK);
	}

}
