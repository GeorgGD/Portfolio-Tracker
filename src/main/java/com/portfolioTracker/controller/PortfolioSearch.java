package com.portfolioTracker.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import com.portfolioTracker.api.APIRequester;
import com.portfolioTracker.api.PortfolioDTO;
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
	 * and automatically add those stocks into their portfolio
	 * @param ticker The ticker of the stock	 
	 * @param numShares The number of shares
	 * @param buyInPrice The price per share
	 * @param username The name of the user
	 * @param currInvestment The total money invested	
	 * @param currWorth The current net worth of the portfolio
	 * @return The view with the data to display	
	 */	
	@RequestMapping(value = "/portfolioSearch", method = RequestMethod.GET)
	public ModelAndView portfolioSearch(PortfolioDTO portDTO,
										@CookieValue(value = "username", defaultValue = "") String username,
										@CookieValue(value = "worth", defaultValue = "0") String currWorth) {
		HashMap<String, String> expValuePair = new HashMap<String, String>();
	    String view = "portfolio";
		String currInvestment = server.checkCurrentInvestment(username);
	    viewHandler.newModelAndView();
		viewHandler.setView(view);
		
		try {
			if(Double.parseDouble(portDTO.getSharesNum()) <= 0 || Double.parseDouble(portDTO.getBuyInPrice()) <= 0) {
				viewHandler = prepCurrentEvalAndTable(viewHandler, currInvestment, currWorth, username);
				return viewHandler.getModelView();
			}			
			String name = api.nameOfCompany(portDTO.getTicker()); // So exception is caught
			viewHandler.addObjectsToView("name", name);
			expValuePair.put("name", name);
		} catch (NumberFormatException e) {
			e.printStackTrace(); 
			viewHandler = prepCurrentEvalAndTable(viewHandler, currInvestment, currWorth, username);
			return viewHandler.getModelView();
		} catch (TickerNotFoundException e) {
			e.printStackTrace();
			viewHandler = prepCurrentEvalAndTable(viewHandler, currInvestment, currWorth, username);	    
			return viewHandler.getModelView(); 
		}
		
		expValuePair.put("shares", portDTO.getSharesNum());
		expValuePair.put("buyInPrice", portDTO.getBuyInPrice());			
		server.addStockToPortfolio(username, portDTO.getTicker(), expValuePair);
		currInvestment = server.checkCurrentInvestment(username);
		
		viewHandler = prepCurrentEvalAndTable(viewHandler, currInvestment, currWorth, username);
	    
		return viewHandler.getModelView();
	}

	/**
	 * Updates the current evaluation of the users portfolio
	 * @param username The name of the user
	 * @param response The HTTP response 
	 * @return The view with the data to display	
	 */	
	@RequestMapping(value = "/updateEval", method = RequestMethod.GET)
	public ModelAndView updateEvaluation(@CookieValue(value = "username", defaultValue = "") String username, HttpServletResponse response) {
	    String view = "portfolio";
		viewHandler.newModelAndView();
		viewHandler.setView(view);
		
		if(username.equals("")) {
			viewHandler = prepCurrentEvalAndTable(viewHandler, "0", "0", username);
			return viewHandler.getModelView();
		}
		
		String currInvestment = server.checkCurrentInvestment(username);
		String currEval = server.updateCurrentEval(username);
		viewHandler = prepCurrentEvalAndTable(viewHandler, currInvestment, currEval, username);
		response = addToCookie(currInvestment, currEval, response);
		
		return viewHandler.getModelView();
	}

	/**
	 * Prepers the current evaluation values
	 * @param viewHandler A objects that handles all the data for the view
	 * @param currInvestment The current investment
	 * @param currWorth The current net worth of the investment
	 * @param username The name of the user	
	 * @return A map with EL and value pair	
	 */
	private ViewHandler prepCurrentEvalAndTable(ViewHandler viewHandler, String currInvestment, String currWorth, String username) {
		viewHandler.addObjectsToView("currentInvestment", currInvestment + " USD");
		viewHandler.addObjectsToView("currentEvaluation", currWorth + " USD");			
		viewHandler.addObjectsToView("tableBody", server.setupTableEntries(username));
		return viewHandler;
	}

	/**
	 * Add values to cookie 
	 * @param currInvestment The current investment 
	 * @param currWorth The current net worth of the investment
	 * @param response The HTTP response body 	
	 * @return The response body	
	 */
	private HttpServletResponse addToCookie(String currInvestment, String currWorth, HttpServletResponse response) {
		response.addCookie(cookieHandler.putInsideCookie("invested", currInvestment));
		response.addCookie(cookieHandler.putInsideCookie("worth", currWorth));
		
		return response;
	}	
}
