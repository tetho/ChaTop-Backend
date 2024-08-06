package com.chatop.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.chatop.dto.UserDTO;

@Service
public class AuthService {

	private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    public AuthService(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean authenticate(String email, String password) {
        Optional<UserDTO> optionalUser = userService.findByEmail(email);
        if (optionalUser.isPresent()) {
            UserDTO user = optionalUser.get();
            return passwordEncoder.matches(password, user.getPassword());
        } else {
            return false;
        }
    }

    public void register(UserDTO user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
    }
    
}
