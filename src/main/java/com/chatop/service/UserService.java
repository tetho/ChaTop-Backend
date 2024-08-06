package com.chatop.service;

import java.util.Optional;
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
	
	public Optional<UserDTO> findByEmail(String email) {
        return userRepository.findByEmail(email)
        		.map(UserMapper.INSTANCE::toDTO);
    }
	
	public Optional<UserDTO> getUser(final Integer id) {
		return userRepository.findById(id)
				.map(UserMapper.INSTANCE::toDTO);
	}
	
	public void delete(final Integer id) {
		if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
	}
	
	public UserDTO save(final UserDTO userDTO) {
		User user = UserMapper.INSTANCE.toEntity(userDTO);
		UserDTO savedUser = UserMapper.INSTANCE.toDTO(userRepository.save(user));
		return savedUser;
	}
}
