package com.chatop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MessageDTO {

	private Integer id;
	
	@JsonProperty("rental_id")
	private Integer rentalId;
	
	@JsonProperty("user_id")
	private Integer userId;
	
	private String message;
}
