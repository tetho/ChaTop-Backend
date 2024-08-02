package com.chatop.configuration;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.chatop.mapper.UserMapper;

@Configuration
public class MapperConfig {
	
	@Bean
    public UserMapper userMapper() {
        return Mappers.getMapper(UserMapper.class);
    }
}
