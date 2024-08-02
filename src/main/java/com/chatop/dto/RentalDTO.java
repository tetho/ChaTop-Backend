package com.chatop.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class RentalDTO {

	private Long id;
	
	private String name;
	
	private BigDecimal surface;
	
	private BigDecimal price;
	
	private String picture;
	
	private String description;
	
	private Long ownerId;
}
