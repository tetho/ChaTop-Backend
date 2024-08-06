package com.chatop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.dto.MessageDTO;
import com.chatop.service.MessageService;

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
	public ResponseEntity<String> createMessage(@RequestBody MessageDTO message) {
		messageService.save(message);
		return new ResponseEntity<>("Message send with success", HttpStatus.OK); 
	}
}
