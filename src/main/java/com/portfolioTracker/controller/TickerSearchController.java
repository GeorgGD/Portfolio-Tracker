package com.portfolioTracker.controller;

import com.portfolioTracker.api.APIRequester;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * This controller is responsable for any searches that revolve around
 * using the ticker to find data about a stock
 * @author Georgios Davakos
 * @since 2020-11-13
 */ 
@Controller
public class TickerSearchController {

	@Autowired
	private APIRequester api;

    /**
	 * Searches for the stock with the given ticker and provides 
	 * the name and current price of the stock. 
	 * @param ticker The ticker of the stock
	 * @return The model and view holding the stocks name and current price	
	 */	
	@RequestMapping("/tickerSearch")
	public String tickerSearch(@RequestParam("ticker") String ticker, Model model) {
		String jspExpression = "result";
		String view = "index";
		
		if(ticker == null || ticker.equals("")) {		    
		    model.addAttribute(jspExpression, "ERROR: Ticker not found!");		    
			return view;
		}
		
		String apiResponseStr = api.currentStockData(ticker);
		
		if(apiResponseStr == null) {
		   model.addAttribute(jspExpression, "ERROR: Server call not found!");
    		return view;
		}
	    model.addAttribute(jspExpression, apiResponseStr);
		return view;
	}
}
