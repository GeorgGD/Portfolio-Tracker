package com.portfolioTracker.serverCom;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UserAuthTest {
	private String FILE_PATH = "src/main/resources/users/";
	
	private UserAuth initialize() {
		UserAuth user = new UserAuth();
		user.setMapper(new ObjectMapper());
		return user;
	}
}
