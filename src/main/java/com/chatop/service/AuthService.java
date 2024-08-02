package com.chatop.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.chatop.dto.RegisterRequestDTO;
import com.chatop.mapper.UserMapper;
import com.chatop.model.DBUser;
import com.chatop.repository.DBUserRepository;

@Service
public class AuthService {

	private final DBUserRepository dbUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Autowired
    public AuthService(DBUserRepository dbUserRepository, BCryptPasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.dbUserRepository = dbUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public boolean authenticate(String username, String password) {
        Optional<DBUser> optionalUser = dbUserRepository.findByUsername(username);
        
        if (optionalUser.isPresent()) {
            DBUser dbUser = optionalUser.get();
            return passwordEncoder.matches(password, dbUser.getPassword());
        } else {
            return false;
        }
    }

    public void register(RegisterRequestDTO registerRequestDTO) {
        DBUser dbUser = userMapper.registerRequestDTOToDBUser(registerRequestDTO);
        dbUser.setPassword(passwordEncoder.encode(dbUser.getPassword()));
        dbUserRepository.save(dbUser);
    }
}
