package com.portfolioTracker.integrationTests;

import static org.junit.Assert.assertNotEquals;

import java.util.HashMap;

import com.portfolioTracker.serverCom.ServerCommunication;

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

public class ServerComIntegratedTest {

	@Autowired
	ServerCommunication server;


		@Test
	public void updateCurrentEvalTest() {
    	HashMap<String,String> stockData = new HashMap<String,String>();
		String expTicker = "MSFT";	
		String expUser = "unitTest";
		
		server.createUser(expUser);
		stockData.put("shares", "20");
		stockData.put("buyInPrice", "5");		
		server.addStockToPortfolio(expUser, expTicker, stockData);

		String actualResult = server.updateCurrentEval(expUser);
		assertNotEquals("0", actualResult);
	}
}
