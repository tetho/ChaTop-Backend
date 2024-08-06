package com.chatop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AuthDTO {

	@JsonProperty("login")
	private String email;
	
	private String password;
}
