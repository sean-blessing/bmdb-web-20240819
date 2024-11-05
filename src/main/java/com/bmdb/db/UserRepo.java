package com.bmdb.db;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bmdb.model.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	public Optional<User> findByUsernameAndPassword(String username, String password);
}
