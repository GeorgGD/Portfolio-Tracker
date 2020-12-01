package com.portfolioTracker.integrationTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Map;

import com.portfolioTracker.controller.PortfolioSearch;
import com.portfolioTracker.dto.PortfolioDTO;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.ModelAndView;

import config.SpringConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
@WebAppConfiguration
public class PortfolioSearchTest {

	@Autowired
	private PortfolioSearch portfolio;

	private PortfolioDTO portDTO;

	private MockHttpSession session;
	
	@Before
	public void setupPortfolioDTO() {
		portDTO = new PortfolioDTO();
		portDTO.setTicker("MSFT");
		portDTO.setSharesNum("20");
		portDTO.setBuyInPrice("5");		
	}

	@Before
	public void setupSession() {
		session = new MockHttpSession();
		String username = "integratedTest";		
		session.setAttribute("username", username);		
	}
	
	@Test
	public void viewTest() {
		ModelAndView mav = portfolio.portfolioSearch(portDTO, session);

		String expectedView = "portfolio";
		assertEquals(expectedView, mav.getViewName());
	}

	@Test
	public void checkAttributeValues() {
		ModelAndView mav = portfolio.portfolioSearch(portDTO, session);

		Map<String, Object> map = mav.getModel();
		String expectedResult = "<tr><td>Microsoft Corporation</td><td>20</td><td>5 USD</td></tr>";
		String elExpresion = "tableBody";
		assertEquals(expectedResult, map.get(elExpresion));
	}

	@Test
	public void updateEvaluationTest() {	    
		portfolio.portfolioSearch(portDTO, session);
		ModelAndView mav = portfolio.updateEvaluation(session);
		Map<String, Object> map = mav.getModel();

		String expectedCurrentValue = "100.0 USD";
		assertEquals(expectedCurrentValue, map.get("currentInvestment"));
		assertNotEquals("0 USD", map.get("currentEvaluation"));		
	}
}
