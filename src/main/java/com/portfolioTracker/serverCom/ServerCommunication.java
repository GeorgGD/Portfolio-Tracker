package com.portfolioTracker.serverCom;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Component;

@Component
public class ServerCommunication {

	public void createUser(String username) {
		try {			
			File user = new File(username + ".txt");
			user.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
									
	}
}
