package com.portfolioTracker.integrationTests;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import com.portfolioTracker.controller.PortfolioSearch;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.ModelAndView;

import config.SpringConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class PortfolioSearchTest {

	@Autowired
	private PortfolioSearch portfolio;

	@Test
	public void viewTest() {
		String ticker = "MSFT";
		String shares = "20";
		String buyInPrice = "5";
		String cookie1 = "integratedTest";
		String cookie2 = "0";
		String cookie3 = "0";
		ModelAndView mav = portfolio.portfolioSearch(ticker, shares, buyInPrice, cookie1, cookie2, cookie3);

		String expectedView = "portfolio";
		assertEquals(expectedView, mav.getViewName());
	}

	@Test
	public void checkAttributeValues() {
		String ticker = "MSFT";
		String shares = "20";
		String buyInPrice = "5";
		String cookie1 = "integratedTest";
		String cookie2 = "0";
		String cookie3 = "0";
		ModelAndView mav = portfolio.portfolioSearch(ticker, shares, buyInPrice, cookie1, cookie2, cookie3);

		Map<String, Object> map = mav.getModel();
		String expectedResult = "<tr><td>Microsoft Corporation</td><td>20</td><td>5 USD</td></tr>";
		String elExpresion = "tableBody";
		assertEquals(expectedResult, map.get(elExpresion));
	}
}
