package com.portfolioTracker.integrationTests;

import static org.junit.Assert.*;

import java.util.HashMap;

import java.io.File;

import com.portfolioTracker.controller.Username;
import com.portfolioTracker.dto.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.MapBindingResult;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class UsernameTest {

	@Autowired
	private Username username;

	private MockHttpSession session;

	private Model model;
	private MapBindingResult errors;
	
	@Before
	public void setup() {
		session = new MockHttpSession();
		model = new ExtendedModelMap();
		errors = new MapBindingResult(new HashMap<String, String>(), "dummy");
	}
	
	@After
	public void removeFiles() {
		String FILE_PATH = "src/main/resources/users/";
		File file = new File(FILE_PATH + "Test.txt");
		file.delete();
	}
	
	@Test
	@Order(1)
	public void loginNonExistingUserTest() {
		User user = new User();
		user.setUsername("Test");
		user.setPassword("123456");
		
		String expectedView = "index";		
		String actualView = username.loginUser(user, errors, model, session);	    
		
		assertEquals(expectedView, actualView);
		
		String expectedMsg = "Login failed, Username and Password didn't match";
		String actualMsg = (String) model.getAttribute("errorMsg");
		assertEquals(expectedMsg, actualMsg);
	}
	
	@Test
	@Order(2)
	public void registerUserTest() {
		User user = new User();
		user.setUsername("Test");
		user.setPassword("123456");
		
		String expectedView = "portfolio";
		String actualView = username.registerUser(user, errors, model, session);
		assertEquals(expectedView, actualView);				
	}
	
	@Test
	@Order(3)
	public void loginExistingUserTest() {
		User user = new User();
		user.setUsername("Test");
		user.setPassword("123456");
		
		String expectedView = "portfolio";
		String actualView = username.loginUser(user, errors, model, session);
        assertEquals(expectedView, actualView);        
	}
}
