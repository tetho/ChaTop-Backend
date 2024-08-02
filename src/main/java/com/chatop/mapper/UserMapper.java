package com.chatop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.chatop.dto.RegisterRequestDTO;
import com.chatop.dto.UserDTO;
import com.chatop.model.DBUser;
import com.chatop.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDTO(User user);
    
    User toEntity(UserDTO userDTO);
    
    DBUser userToDBUser(User user);

    User dbUserToUser(DBUser dbUser);
    
    DBUser registerRequestDTOToDBUser(RegisterRequestDTO registerRequestDTO);
}
