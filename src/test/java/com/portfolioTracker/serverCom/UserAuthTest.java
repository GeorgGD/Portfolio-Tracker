package com.portfolioTracker.serverCom;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolioTracker.dto.User;

public class UserAuthTest {
	private String FILE_PATH = "src/main/resources/users/";
	
	private UserAuth initialize() {
		UserAuth userAuth = new UserAuth();
		userAuth.setMapper(new ObjectMapper());
		return userAuth;
	}
	private User initUser() {
		User user = new User();
		user.setUsername("Jeo");
		user.setPassword("dogsrock");
		return user;
	}
	
	@Test
	public void userIsRegisteredTest() {
		UserAuth userAuth = initialize();
		User user = initUser();
	}
}
