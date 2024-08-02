package com.chatop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.chatop.dto.RentalDTO;
import com.chatop.model.Rental;

@Mapper
public interface RentalMapper {

	RentalMapper INSTANCE = Mappers.getMapper(RentalMapper.class);

    RentalDTO toDTO(Rental rental);
    
    Rental toEntity(RentalDTO rentalDTO);
}
