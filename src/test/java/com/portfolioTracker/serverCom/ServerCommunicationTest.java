package com.portfolioTracker.serverCom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolioTracker.api.YahooAPIRequester;

import org.junit.Test;

public class ServerCommunicationTest {

	private ServerCommunication initialize() {
		ServerCommunication server = new ServerCommunication();
		server.setAPIRequester(new YahooAPIRequester());
		server.setObjectMapper(new ObjectMapper());
		return server;
	}

	private String readPortfolio(String username) {
		File file = new File(username + ".txt");
		String portfolio = "";
		Scanner scan = null;
		
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return "";
		}
		
		while(scan.hasNextLine()) {
			portfolio = portfolio + scan.nextLine();
		}
		
		if(scan != null)
			scan.close();
		return portfolio;

	}
	
	@Test
	public void createUserTest() {
		ServerCommunication server = initialize();
		String expUser = "unitTest";
		server.createUser(expUser);

		File file = new File(expUser + ".txt");
		assertTrue(file.exists());
	}
}
