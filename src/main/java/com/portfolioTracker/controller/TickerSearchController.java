package com.portfolioTracker.controller;

import java.io.IOException;
import java.util.ArrayList;

import com.portfolioTracker.api.APIRequester;

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
		ModelAndView mav = new ModelAndView();
		String view = "index";
		expressions.add("result");
		
		if(ticker == null || ticker.equals("")) {
			values.add("ERROR: Ticker not found!");
			mav = setupModelAndView(mav, expressions, values, view);
			return mav;
		}

		try {
			values.add(api.currentStcokData(ticker));
			if(values.size() == 0) {
				values.add("ERROR: Server call not found!");
				mav = setupModelAndView(mav, expressions, values, view);
				return mav;
			}
			mav = setupModelAndView(mav, expressions, values, view);
			return mav;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return mav;
	}
	
	/** //TODO: REDO DOCS
	 * A helper function for tickerSeearch-method, sets up the model and view
	 * @param mav The model and view
	 * @param values The string that the model and view should hold
	 * @return the modefied model and view	
	 */
	private ModelAndView setupModelAndView(ModelAndView mav, ArrayList<String> expressions, ArrayList<String> values, String view) {
		mav.setViewName(view);

		int size = values.size();
		for (int i = 0; i < size; ++i) {
			mav.addObject(expressions.get(i), values.get(i));
		}

		return mav;
	}
}
