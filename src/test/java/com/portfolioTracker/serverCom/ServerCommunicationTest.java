package com.portfolioTracker.serverCom;

import static org.junit.Assert.assertTrue;

import java.io.File;

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
	
	@Test
	public void createUserTest() {
		ServerCommunication server = initialize();
		String expUser = "unitTest";
		server.createUser(expUser);

		File file = new File(expUser + ".txt");
		assertTrue(file.exists());
	}
}
