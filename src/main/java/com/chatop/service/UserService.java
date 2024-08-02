package com.chatop.service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatop.dto.UserDTO;
import com.chatop.mapper.UserMapper;
import com.chatop.model.User;
import com.chatop.repository.UserRepository;


@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public Optional<UserDTO> findByName(String name) {
        return userRepository.findByName(name)
        		.map(UserMapper.INSTANCE::toDTO);
    }
	
	public Optional<UserDTO> findByEmail(String email) {
        return userRepository.findByEmail(email)
        		.map(UserMapper.INSTANCE::toDTO);
    }
	
	public Optional<UserDTO> getUser(final Long id) {
		return userRepository.findById(id)
				.map(UserMapper.INSTANCE::toDTO);
	}
	
	public Iterable<UserDTO> getUsers() {
		return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(UserMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
	}
	
	public void deleteUser(final Long id) {
		if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
	}
	
	public UserDTO saveUser(final UserDTO userDTO) {
		User user = UserMapper.INSTANCE.toEntity(userDTO);
		UserDTO savedUser = UserMapper.INSTANCE.toDTO(userRepository.save(user));
		return savedUser;
	}
}
