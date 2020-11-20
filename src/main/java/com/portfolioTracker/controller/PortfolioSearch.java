package com.portfolioTracker.controller;

import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.portfolioTracker.api.APIRequester;
import com.portfolioTracker.api.TickerNotFoundException;
import com.portfolioTracker.cookies.CookieHandler;
import com.portfolioTracker.serverCom.ServerCommunication;
import com.portfolioTracker.view.ViewHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * The following class is meant to allow the used to create a portfolio of stocks
 * and determine the performance of the portfolio.
 * @author Georgios Davakos
 * @since 2020-11-19
 */
@Controller
public class PortfolioSearch {

	@Autowired
	private APIRequester api;
	
	@Autowired
	private ViewHandler viewHandler;

	@Autowired
	private CookieHandler cookieHandler;

	@Autowired
	private ServerCommunication server;

	/**
	 * The following method is meant to allow the user to search for stocks
	 * and automatically add those stocks into their portfolio which 
	 * then is saved on a server.
	 * 
	 * TODO: Find an elegant way to keep currentInvestment and currentEvaluation updated as the user adds more stocks!
	 * Method is not 100% implemented yet	
	 */	
	@RequestMapping(value = "/portfolioSearch", method = RequestMethod.GET)
	public ModelAndView portfolioSearch(@RequestParam("ticker") String ticker,
										@RequestParam("sharesNum") String numShares,
										@RequestParam("buyInPrice") String buyInPrice,
										@CookieValue(value = "username", defaultValue = "") String username,
										@CookieValue(value = "invested", defaultValue = "0") String currInvestment,
										@CookieValue(value = "worth", defaultValue = "0") String currWorth) {
		HashMap<String, String> expValuePair = new HashMap<String, String>();
	    String view = "portfolio";
		ModelAndView mav;
		viewHandler.setModelView(new ModelAndView());
		
		try {
			if(Double.parseDouble(numShares) <= 0 || Double.parseDouble(buyInPrice) <= 0) {
				expValuePair = prepCurrentEval(expValuePair, currInvestment, currWorth);
				return viewHandler.setupModelAndView(expValuePair, view);
			}			
			String name = api.nameOfCompany(ticker); // So exception is caught
			expValuePair.put("name", name);					
		} catch (NumberFormatException e) {
			e.printStackTrace(); 
			expValuePair = prepCurrentEval(expValuePair, currInvestment, currWorth);
			return viewHandler.setupModelAndView(expValuePair, view);
		} catch (TickerNotFoundException e) {
			e.printStackTrace();
			expValuePair = prepCurrentEval(expValuePair, currInvestment, currWorth);
			return viewHandler.setupModelAndView(expValuePair, view); 
		}
		
		expValuePair.put("shares", numShares);
		expValuePair.put("buyInPrice", buyInPrice);
		expValuePair = prepCurrentEval(expValuePair, currInvestment, currWorth);
			
		server.addStockToPortfolio(username, ticker, expValuePair);
		
		expValuePair.put("buyInPrice", buyInPrice + " USD");
		mav = viewHandler.setupModelAndView(expValuePair, view);
		return mav;
	}

	/**
	 * Updates the current evaluation of the users portfolio
	 * NOT IMPLEMENTED	
	 */	
	@RequestMapping(value = "/updateEval", method = RequestMethod.GET)
	public ModelAndView updateEvaluation(@CookieValue(value = "username", defaultValue = "") String username) {
		HashMap<String, String> expValuePair = new HashMap<String, String>();
	    String view = "portfolio";
		ModelAndView mav;
		viewHandler.setModelView(new ModelAndView());
	
		if(username.equals("")) {
			expValuePair.put("currentInvestment", "0 USD");
			expValuePair.put("currentEvaluation", "0 USD");			
			return viewHandler.setupModelAndView(expValuePair, view);
		}
		
		String currInvestment = server.updateCurrentInvestment(username);
		String currEval = server.updateCurrentEval(username);
		expValuePair.put("currentInvestment", currInvestment + " USD"); // SAVE AS COOKIE TOO!
		expValuePair.put("currentEvaluation", currEval + " USD");	// SAVE AS COOKIE TOO!
		mav = viewHandler.setupModelAndView(expValuePair, view);
		return mav;
	}

	private HashMap<String, String> prepCurrentEval(HashMap<String, String> expValuePair, String currInvestment, String currWorth) {
		expValuePair.put("currentInvestment", currInvestment + " USD");
		expValuePair.put("currentEvaluation", currWorth + " USD");			
		return expValuePair;
	}
}
