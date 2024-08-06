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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.dto.RentalDTO;
import com.chatop.service.RentalService;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

	RentalService rentalService;
	
	@Autowired
	public RentalController(RentalService rentalService) {
		this.rentalService = rentalService;
	}
	
	@GetMapping
	public Iterable<RentalDTO> getRentals() {
		return rentalService.getRentals();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<RentalDTO> getRental(@PathVariable final Integer id) {
		RentalDTO rentalDTO = rentalService.getRental(id)
				.orElseThrow(() -> new RuntimeException("Rental not found"));
        return new ResponseEntity<>(rentalDTO, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<RentalDTO> createRental(@RequestBody RentalDTO rentalDTO) {
		RentalDTO createdRental = rentalService.save(rentalDTO);
        return new ResponseEntity<>(createdRental, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> updateRental(@PathVariable Integer id) {
		Optional<RentalDTO> optionalRental = rentalService.getRental(id);
		
		if (optionalRental.isPresent()) {
			RentalDTO rental = optionalRental.get();
			
			rentalService.save(rental);
			
			return new ResponseEntity<>("Rental updated !", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Rental not found", HttpStatus.NOT_FOUND);
		}
	}
}
