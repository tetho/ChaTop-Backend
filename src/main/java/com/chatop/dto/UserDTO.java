package com.chatop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserDTO {

	private Integer id;
	
	@NotEmpty(message = "Email is required")
    @Email(message = "Invalid email format")
	private String email;
	
	@NotEmpty(message = "Name is required")
	private String name;
	
	@NotEmpty(message = "Password is required")
	private String password;
}
