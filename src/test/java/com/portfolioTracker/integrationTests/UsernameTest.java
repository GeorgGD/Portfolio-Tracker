package com.portfolioTracker.integrationTests;

import static org.junit.Assert.*;

import java.util.HashMap;

import com.portfolioTracker.controller.Username;
import com.portfolioTracker.dto.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.MapBindingResult;

import config.SpringConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
@WebAppConfiguration
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

	@Test
	public void setUsernameCookieNoInputTest() {
		User user = new User();
		user.setUsername("Test");
		user.setPassword("123456");
		
		String expectedView = "index";		
		String actualView = username.loginUser(user, errors, model, session);	    

		assertEquals(expectedView, actualView);
	}
}
