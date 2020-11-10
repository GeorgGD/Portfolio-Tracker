package com.portfolioTracker.controller;

import com.portfolioTracker.api.APIRequester;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TickerSearchController {

	@Autowired
	private APIRequester api;
	
	@RequestMapping("tickerSearch")
	public ModelAndView tickerSearch(@RequestParam("ticker") String ticker) {
		// TODO: pass ticker to an api class that makes the api call

		// TODO: return the name of the company and current price
		String res;
		ModelAndView mav = new ModelAndView();
		
		if(ticker == null || ticker.equals("")) {
			res = "ERROR: Ticker not found!";
			mav = setupModelAndView(mav, res);
			return mav;
		}
		
		return mav;
	}

	private ModelAndView setupModelAndView(ModelAndView mav, String res) {
		mav.setViewName("index.jsp");
		mav.addObject("result", res);
		return mav;
	}
}
