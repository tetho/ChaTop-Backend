package com.chatop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatop.model.DBUser;

public interface DBUserRepository extends JpaRepository<DBUser, Integer> {
	
	public DBUser findByUsername(String username);
}