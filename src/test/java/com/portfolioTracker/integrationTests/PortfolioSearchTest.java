package com.portfolioTracker.integrationTests;

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
	public void portfolioSearchTest() {
		String ticker = "MSFT";
		String shares = "20";
		String buyInPrice = "5";
		String cookie1 = "integratedTest";
		String cookie2 = "0";
		String cookie3 = "0";
		ModelAndView mav = portfolio.port
	}
}
