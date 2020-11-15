package com.portfolioTracker.controller;

import java.util.ArrayList;

import com.portfolioTracker.api.APIRequester;
import com.portfolioTracker.view.ViewHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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

	@Autowired
	private ViewHandler viewHandler;
	/**
	 * Searches for the stock with the given ticker and provides 
	 * the name and current price of the stock. 
	 * @param ticker The ticker of the stock
	 * @return The model and view holding the stocks name and current price	
	 */	
	@RequestMapping("/tickerSearch")
	public ModelAndView tickerSearch(@RequestParam("ticker") String ticker) {
		ArrayList<String> values = new ArrayList<String>();
		ArrayList<String> expressions = new ArrayList<String>();
		viewHandler.setModelView(new ModelAndView());
		String view = "index";
		expressions.add("result");
		ModelAndView mav;
		
		if(ticker == null || ticker.equals("")) {
			values.add("ERROR: Ticker not found!");
			mav = viewHandler.setupModelAndView(expressions, values, view);
			return mav;
		}
		
		values.add(api.currentStcokData(ticker));
		if(values.size() == 0) {
			values.add("ERROR: Server call not found!");
			mav = viewHandler.setupModelAndView(expressions, values, view);
			return mav;
		}
		mav = viewHandler.setupModelAndView(expressions, values, view);
		return mav;
	}
}
