package com.chatop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class MessageDTO {

	private Integer id;
	
	@JsonProperty("rental_id")
	private Integer rentalId;
	
	@JsonProperty("user_id")
	private Integer userId;
	
	@NotEmpty(message = "Message is required")
	private String message;
}
