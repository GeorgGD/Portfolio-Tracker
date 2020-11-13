package com.portfolioTracker.controller;

import java.io.IOException;

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
	@RequestMapping("tickerSearch")
	public ModelAndView tickerSearch(@RequestParam("ticker") String ticker) {
    	String res;
		ModelAndView mav = new ModelAndView();
		
		if(ticker == null || ticker.equals("")) {
			res = "ERROR: Ticker not found!";
			mav = setupModelAndView(mav, res);
			return mav;
		}

		try {
			res = api.currentPrice(ticker);
			if(res == null) {
				res = "ERROR: Server call not found!";
				mav = setupModelAndView(mav, res);
				return mav;
			}
			mav = setupModelAndView(mav, res);
			return mav;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return mav;
	}
	
	/**
	 * A helper function for tickerSeearch-method, sets up the model and view
	 * @param mav The model and view
	 * @param res the string that the model and view should hold
	 * @return the modefied model and view	
	 */
	private ModelAndView setupModelAndView(ModelAndView mav, String res) {
		mav.setViewName("index.jsp");
		mav.addObject("result", res);
		return mav;
	}
}
