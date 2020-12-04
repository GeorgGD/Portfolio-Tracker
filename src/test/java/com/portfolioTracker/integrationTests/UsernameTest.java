package com.portfolioTracker.integrationTests;

import static org.junit.Assert.*;

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

import config.SpringConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
@WebAppConfiguration
public class UsernameTest {

	@Autowired
	private Username username;

	private MockHttpSession session;

	private Model model;
	
	@Before
	public void setup() {
		session = new MockHttpSession();
		model = new ExtendedModelMap();
	}

/*	@Test
	public void setUsernameCookieNoInputTest() {
		User user = new User();
		user.setUsername("");
		String expectedResult = "Please provide a Username!";
		String expectedView = "index";
		String expectedKey = "errorMsg";
		
		String actualView = username.loginUser(user, result, model, session);	    
		String actualResult = (String) model.getAttribute(expectedKey);

		assertEquals(expectedResult, actualResult);
		assertEquals(expectedView, actualView);
	}*/
}
