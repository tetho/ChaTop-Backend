package com.chatop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.chatop.dto.MessageDTO;
import com.chatop.model.Message;

@Mapper
public interface MessageMapper {

	MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    MessageDTO toDTO(Message message);
    
    Message toEntity(MessageDTO messageDTO);
}
