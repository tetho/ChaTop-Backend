package com.chatop.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class RentalDTO {

	private Integer id;
	
	private String name;
	
	private BigDecimal surface;
	
	private BigDecimal price;
	
	private String picture;
	
	private String description;
	
	@JsonProperty("owner_id")
	private Integer ownerId;
	
	@JsonProperty("created_at")
	private LocalDateTime createdAt;
	
	@JsonProperty("updated_at")
	private LocalDateTime updatedAt;
}
