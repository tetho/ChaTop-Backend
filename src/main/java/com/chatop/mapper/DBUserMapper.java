package com.chatop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.chatop.dto.DBUserDTO;
import com.chatop.model.DBUser;

@Mapper
public interface DBUserMapper {

	DBUserMapper INSTANCE = Mappers.getMapper(DBUserMapper.class);

    DBUserDTO toDTO(DBUser user);
    
    DBUser toEntity(DBUserDTO userDTO);
}
