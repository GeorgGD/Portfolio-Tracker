package com.portfolioTracker.integrationTests;

import static org.junit.Assert.*;

import java.util.HashMap;

import com.portfolioTracker.controller.TickerSearchController;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import config.SpringConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
@WebAppConfiguration
public class TickerSearchControllerTest {

	@Autowired
	private TickerSearchController searchController;

	private Model model;

	@Before
	private void setupModel() {
		model = new ExtendedModelMap();
	}
	
	@Test
	public void tickerSearchNoInputTest() {
		String view = searchController.tickerSearch("", model);
	    String expectedResult = "ERROR: Ticker not found!";
		String actualResult = (String) model.getAttribute("result");

		assertEquals(expectedResult, actualResult);
	}
	
	@Test
	public void tickerSearchTest() {
		String view = searchController.tickerSearch("MSFT", model);
    	String notExpectedResult = "ERROR: Ticker not found!";
		String actualResult = (String) model.getAttribute("result");

		assertNotEquals(notExpectedResult, actualResult);
		assertNotEquals("", actualResult);
	}
}
