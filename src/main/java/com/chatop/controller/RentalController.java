package com.chatop.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/rentals")
@Tag(name = "Rental Controller", description = "Gestion des locations")
public class RentalController {

	RentalService rentalService;
	
	@Autowired
	public RentalController(RentalService rentalService) {
		this.rentalService = rentalService;
	}
	
	@GetMapping
	@Operation(summary = "Récupérer toutes les locations", description = "Renvoie la liste de toutes les locations.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des locations récupérée avec succès"),
        @ApiResponse(responseCode = "401", description = "Utilisateur non authentifié", content = @Content(
        		mediaType = MediaType.APPLICATION_JSON_VALUE, 
        		schema = @Schema(implementation = Object.class)))
    })
	public Iterable<RentalDTO> getRentals() {
		return rentalService.getRentals();
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Récupérer une location par son identifiant", description = "Renvoie une location spécifique par son identifiant.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Location récupérée avec succès"),
        @ApiResponse(responseCode = "401", description = "Utilisateur non authentifié", content = @Content(
        		mediaType = MediaType.APPLICATION_JSON_VALUE, 
        		schema = @Schema(implementation = Object.class)))
    })
	public ResponseEntity<RentalDTO> getRental(@PathVariable final Integer id) {
		RentalDTO rentalDTO = rentalService.getRental(id)
				.orElseThrow(() -> new RuntimeException("Rental not found"));
        return new ResponseEntity<>(rentalDTO, HttpStatus.OK);
	}
	
	@PostMapping
	@Operation(summary = "Créer une nouvelle location", description = "Crée et enregistre une nouvelle location.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Location créée avec succès"),
        @ApiResponse(responseCode = "401", description = "Utilisateur non authentifié", content = @Content(
        		mediaType = MediaType.APPLICATION_JSON_VALUE, 
        		schema = @Schema(implementation = Object.class))),
    })
	public ResponseEntity<RentalDTO> createRental(@RequestBody RentalDTO rentalDTO) {
		RentalDTO createdRental = rentalService.save(rentalDTO);
        return new ResponseEntity<>(createdRental, HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	@Operation(summary = "Mettre à jour une location", description = "Met à jour une location existante.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Location mise à jour avec succès"),
        @ApiResponse(responseCode = "401", description = "Utilisateur non authentifié", content = @Content(
        		mediaType = MediaType.APPLICATION_JSON_VALUE, 
        		schema = @Schema(implementation = Object.class))),
    })
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
