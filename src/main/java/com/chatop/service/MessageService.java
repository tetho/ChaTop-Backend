package com.chatop.service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatop.dto.MessageDTO;
import com.chatop.mapper.MessageMapper;
import com.chatop.model.Message;
import com.chatop.repository.MessageRepository;

@Service
public class MessageService {

	@Autowired
	MessageRepository messageRepository;
	
	public Optional<MessageDTO> getMessage(final Long id) {
		return messageRepository.findById(id)
				.map(MessageMapper.INSTANCE::toDTO);
	}
	
	public Iterable<MessageDTO> getMessages() {
		return StreamSupport.stream(messageRepository.findAll().spliterator(), false)
                .map(MessageMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
	}
	
	public void deleteMessage(final Long id) {
		if (messageRepository.existsById(id)) {
			messageRepository.deleteById(id);	
		} else {
			throw new RuntimeException("Message not found with id: " + id);
		}
	}
	
	public Message saveMessage(final Message message) {
		Message savedMessage = messageRepository.save(message);
		return savedMessage;
	}
}
