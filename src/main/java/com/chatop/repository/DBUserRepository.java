package com.chatop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatop.model.DBUser;

public interface DBUserRepository extends JpaRepository<DBUser, Long> {
	
	public Optional<DBUser> findByUsername(String username);
}