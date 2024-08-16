package com.chatop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.dto.UserDTO;
import com.chatop.service.UserService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User Controller", description = "Gestion des utilisateurs")
public class UserController {

	UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/{id}")
	@Operation(summary = "Récupérer un utilisateur par son identifiant", description = "Renvoie un utilisateur spécifique par son identifiant.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Utilisateur récupéré avec succès"),
        @ApiResponse(responseCode = "401", description = "Utilisateur non authentifié", content = @Content(
        		mediaType = MediaType.APPLICATION_JSON_VALUE, 
        		schema = @Schema(implementation = Object.class))),
    })
	public ResponseEntity<UserDTO> getUser(@PathVariable Integer id) {
		UserDTO userDTO = userService.getUser(id)
				.orElseThrow(() -> new RuntimeException("User not found"));;
		return new ResponseEntity<>(userDTO, HttpStatus.OK);
	}

}
