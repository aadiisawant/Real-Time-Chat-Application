package com.chatroom.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import com.chatroom.Repo.MessageRepo;
import com.chatroom.model.Messages;

@RestController
public class ChatController {

	@Autowired
	private MessageRepo messageRepo;
	
	@MessageMapping("/chat")
	@SendTo("/topic/messages")
	public List<Messages> chatHandle(Messages msg){
		msg.setTimestamp(LocalDateTime.now());
		messageRepo.save(msg);
		return messageRepo.findAllByOrderByTimestampDesc();
	}
}
