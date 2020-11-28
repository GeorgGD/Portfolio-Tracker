package com.portfolioTracker.integrationTests;

import static org.junit.Assert.*;

import java.util.HashMap;

import com.portfolioTracker.controller.TickerSearchController;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.ModelAndView;

import config.SpringConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
@WebAppConfiguration
public class TickerSearchControllerTest {

	@Autowired
	private TickerSearchController searchController;

	@Test
	public void tickerSearchNoInputTest() {
		ModelAndView mav = searchController.tickerSearch("");
		HashMap<String, Object> map = mav.getModelMap();
		String expectedResult = "ERROR: Ticker not found!";
		String actualResult = (String) map.get("result");

		assertEquals(expectedResult, actualResult);
	}
	
	@Test
	public void tickerSearchTest() {
		ModelAndView mav = searchController.tickerSearch("MSFT");
		HashMap<String, Object> map = mav.getModelMap();
		String notExpectedResult = "ERROR: Ticker not found!";
		String actualResult = (String) map.get("result");

		assertNotEquals(notExpectedResult, actualResult);
		assertNotEquals("", actualResult);
	}
}
