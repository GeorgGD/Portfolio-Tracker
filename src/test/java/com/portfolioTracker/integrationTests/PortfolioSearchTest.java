package com.portfolioTracker.integrationTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Map;

import com.portfolioTracker.api.PortfolioDTO;
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

	@Autowired
	private PortfolioDTO portDTO;

	@Before
	public void setupPortfolioDTO() {
		portDTO.setTicker("MSFT");
		portDTO.setSharesNum("20");
		portDTO.setBuyInPrice("5");		
	}

	@Test
	public void viewTest() {
		String username = "integratedTest";
		String cookie2 = "0";
		String cookie3 = "0";
		ModelAndView mav = portfolio.portfolioSearch(portDTO, username, cookie2, cookie3);

		String expectedView = "portfolio";
		assertEquals(expectedView, mav.getViewName());
	}

	@Test
	public void checkAttributeValues() {
    	String username = "integratedTest";
		String cookie2 = "0";
		String cookie3 = "0";
		ModelAndView mav = portfolio.portfolioSearch(portDTO, username, cookie2, cookie3);

		Map<String, Object> map = mav.getModel();
		String expectedResult = "<tr><td>Microsoft Corporation</td><td>20</td><td>5 USD</td></tr>";
		String elExpresion = "tableBody";
		assertEquals(expectedResult, map.get(elExpresion));
	}

	@Test
	public void updateEvaluationTest() {	    
		String username = "integratedTest";
		String cookie2 = "0";
		String cookie3 = "0";
		MockHttpServletResponse response = new MockHttpServletResponse();

		portfolio.portfolioSearch(portDTO, username, cookie2, cookie3);
		ModelAndView mav = portfolio.updateEvaluation(username, response);
		Map<String, Object> map = mav.getModel();

		String expectedCurrentValue = "100.0 USD";
		assertEquals(expectedCurrentValue, map.get("currentInvestment"));
		assertNotEquals("0 USD", map.get("currentEvaluation"));		
	}
}
