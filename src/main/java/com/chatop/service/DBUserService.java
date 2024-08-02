package com.chatop.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatop.dto.DBUserDTO;
import com.chatop.mapper.DBUserMapper;
import com.chatop.model.DBUser;
import com.chatop.repository.DBUserRepository;

@Service
public class DBUserService {

	@Autowired
	private DBUserRepository dbUserRepository;
	
	public Optional<DBUserDTO> findByUsername(String username) {
        return dbUserRepository.findByUsername(username)
        		.map(DBUserMapper.INSTANCE::toDTO);
    }
	
	public DBUserDTO saveDBUser(DBUserDTO dbUserDTO) {
		DBUser dbUser = DBUserMapper.INSTANCE.toEntity(dbUserDTO);
        DBUserDTO savedDBUser = DBUserMapper.INSTANCE.toDTO(dbUserRepository.save(dbUser));
        return savedDBUser;
    }
}
