package com.chatop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.dto.MessageDTO;
import com.chatop.model.Message;
import com.chatop.service.MessageService;

@RestController
public class MessageController {

	@Autowired
	MessageService messageService;
	
	@GetMapping("/messages")
	public Iterable<MessageDTO> getMessages() {
		return messageService.getMessages();
	}
	
	@PostMapping("/messages")
	public ResponseEntity<String> createMessage(@RequestBody Message message) {
		return new ResponseEntity<>("Message send with success", HttpStatus.OK); 
	}
}
