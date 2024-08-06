package com.chatop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.chatop.dto.UserDTO;
import com.chatop.model.User;

@Mapper
public interface UserMapper {

	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDTO(User user);
    
    User toEntity(UserDTO userDTO);
}
