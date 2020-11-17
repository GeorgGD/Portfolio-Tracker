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
		
		int shares;
		double price;

		try {
			shares = Integer.parseInt(numShares);
			price = Double.parseDouble(buyInPrice);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		}
		// Prep current investment and total investment

		// Make api request to get name and currentPrice
	    expValuePair.put("name", api.nameOfCompany(ticker));
		expValuePair.put("shares", numShares);
		expValuePair.put("buyInPrice", buyInPrice);
		
		expValuePair.put("currentInvestment", "0 USD");
		expValuePair.put("currentEvaluation", "0 USD");
		mav = viewHandler.setupModelAndView(expValuePair, view);
		return mav;
	}
}
