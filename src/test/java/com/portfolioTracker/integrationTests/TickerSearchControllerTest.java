package com.portfolioTracker.integrationTests;

import static org.junit.Assert.*;

import com.portfolioTracker.controller.TickerSearchController;
import com.portfolioTracker.dto.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TickerSearchControllerTest {

	@Autowired
	private TickerSearchController searchController;

	private Model model;
	private User user;
	
	@Before
	private void setupModel() {
		model = new ExtendedModelMap();
		user = new User();
	}
	
	@Test
	public void tickerSearchNoInputTest() {
		String view = searchController.tickerSearch(user, "", model);
	    String expectedResult = "ERROR: Ticker not found!";
		String actualResult = (String) model.getAttribute("result");

		assertEquals(expectedResult, actualResult);
	}
	
	@Test
	public void tickerSearchTest() {
		String view = searchController.tickerSearch(user, "MSFT", model);
    	String notExpectedResult = "ERROR: Ticker not found!";
		String actualResult = (String) model.getAttribute("result");

		assertNotEquals(notExpectedResult, actualResult);
		assertNotEquals("", actualResult);
	}
}
