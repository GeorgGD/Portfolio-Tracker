package com.portfolioTracker.integrationTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import config.SpringConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
@WebAppConfiguration
public class PortfolioSearchTest {

	@Autowired
	private PortfolioSearch portfolio;

	private PortfolioDTO portDTO;

	private MockHttpSession session;

	private Model model;
	
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

	@Before
	public void setupModel() {
		model = new ExtendedModelMap();
	}
		
	@Test
	public void viewTest() {
		String actualView = portfolio.portfolioSearch(portDTO, model, session);

		String expectedView = "portfolio";
		assertEquals(expectedView, actualView);
	}

	@Test
	public void checkAttributeValues() {
    	String view = portfolio.portfolioSearch(portDTO,model, session);
	    
		String expectedResult = "<tr><td>Microsoft Corporation</td><td>20</td><td>5 USD</td></tr>";
		String elExpresion = "tableBody";
		assertEquals(expectedResult, (String) model.getAttribute(elExpresion));
	}

	@Test
	public void updateEvaluationTest() {
    	portfolio.portfolioSearch(portDTO, model, session);
		String view = portfolio.updateEvaluation(model, session);	    

		String expectedCurrentValue = "100.0 USD";
		assertEquals(expectedCurrentValue, (String) model.getAttribute("currentInvestment"));
		assertNotEquals("0 USD", (String) model.getAttribute("currentEvaluation"));		
	}
}
