package com.portfolioTracker.serverCom;

import java.io.File;
import java.net.URL;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.portfolioTracker.dto.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserAuth {

	@Autowired
	private ObjectMapper mapper;
	
	URL url = this.getClass().getResource("/users/"); // URL to resources 

	public boolean userIsRegistered(User user) {
		if(user.getUsername() == null || user.getUsername().equals(""))
			return false;		
	    
		File userFile = new File(url.toString() + user.getUsername() + ".txt");

		if(!userFile.exists())
			return false;
		
		return true;
	}
	
	public boolean loginSuccess(User user) {
		if(!userIsRegistered(user))
			return false;
		
		String userInfo = readUserFile(url.toString() + user.getUsername() + ".txt");
		try {
			ObjectNode rootNode = (ObjectNode) mapper.readTree(userInfo);
			ObjectNode passNode = (ObjectNode) rootNode.get("password");
			if(!user.getPassword().equals(passNode.asText()))
				return false;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
	private String readUserFile(String string) {
		return null;
	}
}
