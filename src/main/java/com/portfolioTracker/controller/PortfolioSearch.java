package com.portfolioTracker.controller;

import java.util.HashMap;

import com.portfolioTracker.api.APIRequester;
import com.portfolioTracker.cookies.CookieHandler;
import com.portfolioTracker.view.ViewHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PortfolioSearch {

	@Autowired
	private APIRequester api;
	
	@Autowired
	private ViewHandler viewHandler;

	@Autowired
	private CookieHandler cookieHandler;
	
	@RequestMapping(value = "/portfolioSearch", method = RequestMethod.GET)
	public ModelAndView portfolioSearch(@RequestParam("ticker") String ticker, @RequestParam("sharesNum") String numShares, @RequestParam("buyInPrice") String buyInPrice) {
		HashMap<String, String> expValuePair = new HashMap<String, String>();
	    String view = "portfolio";
		ModelAndView mav;
		viewHandler.setModelView(new ModelAndView());
		

		try {
			expValuePair = updateCurrentEvaluation(expValuePair, numShares, buyInPrice, ticker);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			expValuePair.put("currentInvestment", "0 USD");
			expValuePair.put("currentEvaluation", "0 USD");			
			return viewHandler.setupModelAndView(expValuePair, view);
		} catch (TickerNotFoundException e) {
			e.printStackTrace();
			expValuePair.put("currentInvestment", "0 USD");
			expValuePair.put("currentEvaluation", "0 USD");			
			return viewHandler.setupModelAndView(expValuePair, view); 
		}
		// Prep current investment and total investment

		// Make api request to get name and currentPrice
	    expValuePair.put("name", api.nameOfCompany(ticker));
		expValuePair.put("shares", numShares);
		expValuePair.put("buyInPrice", buyInPrice + " USD");
		
		mav = viewHandler.setupModelAndView(expValuePair, view);
		return mav;
	}

	/**
	 * Updates the current evaluation, this method will be changed in the future!
	 */
	private HashMap<String,String> updateCurrentEvaluation(HashMap<String,String> expValuePair, String numShares, String buyInPrice, String ticker) throws NumberFormatException, TickerNotFoundException {
		int shares;
		double price;
		
		shares = Integer.parseInt(numShares);
		price = Double.parseDouble(buyInPrice);
		if(shares < 1 || price <= 0) {
			throw new NumberFormatException("Number of shares were either less than 1 or price was less or equal to 0");		    		
		}

		double currPrice = api.currentPrice(ticker);

		if(currPrice < 0) {
			throw new TickerNotFoundException("Ticker " + ticker + " doesn't seem to belong to a company");		
		}
		
		expValuePair.put("currentInvestment",shares*price + " USD");
		expValuePair.put("currentEvaluation", shares*currPrice + " USD");
		
		return expValuePair;
	}
}
