package com.chatop.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.dto.RentalDTO;
import com.chatop.service.RentalService;

@RestController
public class RentalController {

	@Autowired
	RentalService rentalService;
	
	@GetMapping("/rentals")
	public Iterable<RentalDTO> getRentals() {
		return rentalService.getRentals();
	}
	
	@GetMapping("/rentals/:id")
	public ResponseEntity<RentalDTO> getRental(@PathVariable final Long id) {
		RentalDTO rentalDTO = rentalService.getRental(id)
				.orElseThrow(() -> new RuntimeException("Rental not found"));
        return new ResponseEntity<>(rentalDTO, HttpStatus.OK);
	}
	
	@PostMapping("/rentals")
	public ResponseEntity<RentalDTO> createRental(@RequestBody RentalDTO rentalDTO) {
		RentalDTO createdRental = rentalService.saveRental(rentalDTO);
        return new ResponseEntity<>(createdRental, HttpStatus.CREATED);
	}
	
	@PutMapping("/rentals/:id")
	public ResponseEntity<String> updateRental(@PathVariable Long id) {
		Optional<RentalDTO> optionalRental = rentalService.getRental(id);
		
		if (optionalRental.isPresent()) {
			RentalDTO rental = optionalRental.get();
			
			rentalService.saveRental(rental);
			
			return new ResponseEntity<>("Rental updated !", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Rental not found", HttpStatus.NOT_FOUND);
		}
	}
}
