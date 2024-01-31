package com.chatroom.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatroom.model.Messages;

public interface MessageRepo extends JpaRepository<Messages, Long> {
	List<Messages> findAllByOrderByTimestampDesc();
}
