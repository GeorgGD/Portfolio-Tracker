package com.portfolioTracker.integrationTests;

import static org.junit.Assert.*;

import java.util.HashMap;

import com.portfolioTracker.controller.Username;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.ModelAndView;

import config.SpringConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
@WebAppConfiguration
public class UsernameTest {

	@Autowired
	private Username username;

	private MockHttpServletResponse response;
	
	@Before
	public void setup() {
		response = new MockHttpServletResponse();
	}
	
	@Test
	public void setUsernameCookieNoInputTest() {
		String name = "";
		String expectedResult = "Please provide a Username!";
		String expectedView = "index";
		String expectedKey = "errorMsg";
		
		ModelAndView mav = username.setUsernameCookie(name, response);
		HashMap<String, Object> map = mav.getModelMap();
		String actualResult = (String) map.get(expectedKey);

		assertEquals(expectedResult, actualResult);
		assertEquals(expectedView, mav.getViewName());
	}

}
