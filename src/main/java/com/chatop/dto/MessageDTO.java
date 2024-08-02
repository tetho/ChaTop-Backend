package com.chatop.dto;

import lombok.Data;

@Data
public class MessageDTO {

	private Long id;
	
	private Long rentalId;
	
	private Long userId;
	
	private String message;
}
