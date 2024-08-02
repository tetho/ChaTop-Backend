package com.chatop.dto;

import lombok.Data;

@Data
public class DBUserDTO {

	private Long id;
	
	private String username;
	
	private String password;
	
	private String role;
	
	private String email;
	
	private String name;
}
