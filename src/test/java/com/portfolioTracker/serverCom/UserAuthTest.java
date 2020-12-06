package com.portfolioTracker.serverCom;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
	
	private String readUserFile(String username) {
		File file = new File(FILE_PATH + username + ".txt");
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
	public void userIsRegisteredTest() {
		UserAuth userAuth = initialize();
		User user = initUser();
		user.setUsername("Stig");
		boolean expectedRegistered = false;
		boolean actuallyRegistered = userAuth.userIsRegistered(user);
		assertEquals(expectedRegistered, actuallyRegistered);
	}
	
	@Test
	public void registerUserTest() {
		UserAuth userAuth = initialize();
		User user = initUser();
		String expectedFileOutput = "";
		String actualFileOutput = readUserFile(user.getUsername());
		assertEquals(expectedFileOutput, actualFileOutput);
		
		expectedFileOutput = "{\"password\":\"dogsrock\"}";
		userAuth.registerUser(user);
		actualFileOutput =readUserFile(user.getUsername());
		assertEquals(expectedFileOutput, actualFileOutput);
		
		boolean expectedVal = true;
		boolean actualVal = userAuth.userIsRegistered(user);
		assertEquals(expectedVal, actualVal);
	}
	
	@Test
	public void loginSuccessTest() {
		UserAuth userAuth = initialize();
		User user = initUser();
		boolean expectedVal = true;
		boolean actualVal = userAuth.loginSuccess(user);
		assertEquals(expectedVal, actualVal);
	}	
}
