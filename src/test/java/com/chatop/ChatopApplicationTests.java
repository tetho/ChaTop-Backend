package com.chatop;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.chatop.controller.AuthController;

@SpringBootTest
class ChatopApplicationTests {

	@Autowired
	private AuthController authController;

	@Test
	void contextLoads() throws Exception {
		assertThat(authController).isNotNull();
	}

}
