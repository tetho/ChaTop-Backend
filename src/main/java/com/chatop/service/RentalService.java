package com.chatop.service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatop.dto.RentalDTO;
import com.chatop.mapper.RentalMapper;
import com.chatop.model.Rental;
import com.chatop.repository.RentalRepository;

@Service
public class RentalService {

	@Autowired
	RentalRepository rentalRepository;
	
	public Optional<RentalDTO> getRental(final Integer id) {
		return rentalRepository.findById(id)
				.map(RentalMapper.INSTANCE::toDTO);
	}
	
	public Iterable<RentalDTO> getRentals() {
		return StreamSupport.stream(rentalRepository.findAll().spliterator(), false)
                .map(RentalMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
	}
	
	public void delete(final Integer id) {
		if (rentalRepository.existsById(id)) {
			rentalRepository.deleteById(id);			
		} else {
            throw new RuntimeException("Rental not found with id: " + id);
        }
	}
	
	public RentalDTO save(final RentalDTO rentalDTO) {
		Rental rental = RentalMapper.INSTANCE.toEntity(rentalDTO);
		rental.setName(rentalDTO.getName());
        rental.setSurface(rentalDTO.getSurface());
        rental.setPrice(rentalDTO.getPrice());
        rental.setPicture(rentalDTO.getPicture());
        rental.setDescription(rentalDTO.getDescription());
        rental.setOwnerId(rentalDTO.getOwnerId());
		RentalDTO savedRental = RentalMapper.INSTANCE.toDTO(rentalRepository.save(rental));
		return savedRental;
	}
}
