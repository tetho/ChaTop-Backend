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
	
	public Optional<RentalDTO> getRental(final Long id) {
		return rentalRepository.findById(id)
				.map(RentalMapper.INSTANCE::toDTO);
	}
	
	public Iterable<RentalDTO> getRentals() {
		return StreamSupport.stream(rentalRepository.findAll().spliterator(), false)
                .map(RentalMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
	}
	
	public void deleteRental(final Long id) {
		if (rentalRepository.existsById(id)) {
			rentalRepository.deleteById(id);			
		} else {
            throw new RuntimeException("Rental not found with id: " + id);
        }
	}
	
	public RentalDTO saveRental(final RentalDTO rentalDTO) {
		Rental rental = RentalMapper.INSTANCE.toEntity(rentalDTO);
		RentalDTO savedRental = RentalMapper.INSTANCE.toDTO(rentalRepository.save(rental));
		return savedRental;
	}
}
