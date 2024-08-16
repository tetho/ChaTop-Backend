package com.chatop.controller;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.dto.MessageDTO;
import com.chatop.service.MessageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/messages")
@Tag(name = "Message Controller", description = "Gestion des messages")
public class MessageController {

	MessageService messageService;
	
	@Autowired
	public MessageController(MessageService messageService) {
		this.messageService = messageService;
	}
	
	@PostMapping
	@Operation(summary = "Créer un message", description = "Crée et enregistre un nouveau message.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Message envoyé avec succès"),
        @ApiResponse(responseCode = "400", description = "Requête invalide, données du message incorrectes"),
        @ApiResponse(responseCode = "401", description = "Utilisateur non authentifié", content = @Content(
        		mediaType = MediaType.APPLICATION_JSON_VALUE, 
        		schema = @Schema(implementation = Object.class)))
    })
	public ResponseEntity<String> createMessage(@Valid @RequestBody MessageDTO message, BindingResult result) {
		if (result.hasErrors()) {
			String errorMessages = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
			return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
		} else {
			messageService.save(message);
			return new ResponseEntity<>("Message send with success", HttpStatus.OK);
		}
	}
}
