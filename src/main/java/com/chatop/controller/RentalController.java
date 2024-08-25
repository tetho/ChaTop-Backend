package com.chatop.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.chatop.dto.RentalDTO;
import com.chatop.dto.RentalsDTO;
import com.chatop.dto.UserDTO;
import com.chatop.service.RentalService;
import com.chatop.service.UserService;
import com.chatop.utils.CustomApiResponse;

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
	UserService userService;
	
	@Autowired
	public RentalController(RentalService rentalService, UserService userService) {
		this.rentalService = rentalService;
		this.userService = userService;
	}
	
	@GetMapping
	@Operation(summary = "Récupérer toutes les locations", description = "Renvoie la liste de toutes les locations.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des locations récupérée avec succès"),
        @ApiResponse(responseCode = "401", description = "Utilisateur non authentifié", content = @Content(
        		mediaType = MediaType.APPLICATION_JSON_VALUE, 
        		schema = @Schema(implementation = Object.class)))
    })
	public ResponseEntity<RentalsDTO> getRentals() {
		RentalsDTO rentalsDTO = rentalService.getRentals();
        return new ResponseEntity<>(rentalsDTO, HttpStatus.OK);
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
	public ResponseEntity<CustomApiResponse<RentalDTO>> createRental(
			@RequestParam("name") String name,
	        @RequestParam("surface") BigDecimal surface,
	        @RequestParam("price") BigDecimal price,
	        @RequestParam("description") String description,
	        @RequestParam("picture") MultipartFile picture,
	        Authentication authentication) {
		String picturePath = uploadPicture(picture);
		
		String email = authentication.getName();
		UserDTO userDTO = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Integer ownerId = userDTO.getId();
		
	    RentalDTO rentalDTO = new RentalDTO();
	    rentalDTO.setName(name);
	    rentalDTO.setSurface(surface);
	    rentalDTO.setPrice(price);
	    rentalDTO.setDescription(description);
	    rentalDTO.setOwnerId(ownerId);
	    rentalDTO.setPicture(picturePath);
	    rentalService.save(rentalDTO);
	    CustomApiResponse<RentalDTO> response = new CustomApiResponse<>(rentalDTO, "Rental created !");
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	@Operation(summary = "Mettre à jour une location", description = "Met à jour une location existante.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Location mise à jour avec succès"),
        @ApiResponse(responseCode = "401", description = "Utilisateur non authentifié", content = @Content(
        		mediaType = MediaType.APPLICATION_JSON_VALUE, 
        		schema = @Schema(implementation = Object.class))),
    })
	public ResponseEntity<CustomApiResponse<RentalDTO>> updateRental(
			@PathVariable Integer id,
			@RequestParam("name") String name,
	        @RequestParam("surface") BigDecimal surface,
	        @RequestParam("price") BigDecimal price,
	        @RequestParam("description") String description) {
		RentalDTO rentalDTO = rentalService.getRental(id)
				.orElseThrow(() -> new RuntimeException("Rental not found"));
		rentalDTO.setName(name);
	    rentalDTO.setSurface(surface);
	    rentalDTO.setPrice(price);
	    rentalDTO.setDescription(description);
	    rentalService.save(rentalDTO);
	    CustomApiResponse<RentalDTO> response = new CustomApiResponse<>(rentalDTO, "Rental updated !");
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}

    private String uploadPicture(MultipartFile picture) {
        if (picture.isEmpty()) {
            throw new IllegalArgumentException("Picture file is empty");
        }

        try {
        	String uploadDir = "src/main/resources/static/uploads/";
            
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            
            String fileName = UUID.randomUUID().toString() + "_" + picture.getOriginalFilename();

            Path filePath = Paths.get(uploadDir, fileName);
            Files.createDirectories(filePath.getParent());
            Files.copy(picture.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return "http://localhost:3001/uploads/" + fileName;

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload picture", e);
        }
    }
}
