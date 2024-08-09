package com.chatop.controller;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.dto.MessageDTO;
import com.chatop.service.MessageService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

	MessageService messageService;
	
	@Autowired
	public MessageController(MessageService messageService) {
		this.messageService = messageService;
	}
	
	@GetMapping
	public Iterable<MessageDTO> getMessages() {
		return messageService.getMessages();
	}
	
	@PostMapping
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
