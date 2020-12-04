package com.portfolioTracker.serverCom;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.portfolioTracker.dto.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * UserAuth is designed to authenticate and authorize the user to use 
 * some of the services requiring authorization 
 * @author Georgios Davakos
 * @since 2020-12-02
 */
@Component
public class UserAuth {

	@Autowired
	private ObjectMapper mapper;
	
	private String filePath = "src/main/resources/users/";
	/**
	 * Checks if user is registered
	 * @param user A user
	 * @return True if a user is registered	
	 */
	public boolean userIsRegistered(User user) {
		if(user.getUsername() == null || user.getUsername().equals(""))
			return false;		
	    
		File userFile = new File(filePath + user.getUsername() + ".txt");
		if(!userFile.exists())
			return false;
		
		return true;
	}

	/**
	 * Checks if the user provided the correct username and password 
	 * @param user The user
	 * @return True if the information provided is accurate	
	 */
	public boolean loginSuccess(User user) {
		if(!userIsRegistered(user))
			return false;
		
		String userInfo = readUserFile(filePath + user.getUsername() + ".txt");
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

	/**
	 * Reads the file that stores the users information
	 * @param userDir The path to the users file
	 * @return The information inside the users file	
	 */
	private String readUserFile(String userDir) {
		File file = new File(userDir);
		String userInfo = "";
		Scanner scan = null;

		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "";
		}
		
		while(scan.hasNextLine()) {
			userInfo = userInfo + scan.nextLine();
		}

		if(scan != null)
			scan.close();
		
		return userInfo;
	}

	/**
	 * Registers the user
	 * @param user The user
	 * @return True if the user was successfully registered	
	 */
	public boolean registerUser(User user) {
		if(userIsRegistered(user))
			return false;

		ObjectNode node = mapper.createObjectNode();
		node.put("password", user.getPassword());
		saveUser(user.getUsername(), node.toString());
		
		return true;
	}

	/**
	 * Saves user
	 * @param username The name of the user
	 * @param toSave The content to save
	 */
	private void saveUser(String username, String toSave) {
		if(username.equals(""))
			return;
		
		File user = new File(filePath + username + ".txt");
		FileWriter toWrite = null;
		try {
			user.createNewFile();		
			toWrite = new FileWriter(username + ".txt", false);
			toWrite.write(toSave);
			System.out.println(username + ".txt");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (toWrite != null) {
				try {
					toWrite.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}			
	}
	
}
