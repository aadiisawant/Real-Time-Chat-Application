package com.chatroom.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatroom.model.User;

public interface UserRepo extends JpaRepository<User, Long> {

	User findByUsername(String username);
}
