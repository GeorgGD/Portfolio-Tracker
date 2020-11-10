package com.portfolioTracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TickerSearchController {

	@RequestMapping("tickerSearch")
	public ModelAndView tickerSearch(@RequestParam("ticker") String ticker) {
		// TODO: pass ticker to an api class that makes the api call

		// TODO: return the name of the company and current price
		String res;
		ModelAndView mav = new ModelAndView();
		
		if(ticker == null || ticker.equals("")) {
			return null;
		}
		
		return mav;
	}
}
