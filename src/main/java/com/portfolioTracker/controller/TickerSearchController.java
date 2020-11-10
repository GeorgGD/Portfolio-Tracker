package com.portfolioTracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TickerSearchController {

	public String tickerSearch(@RequestParam("ticker") String ticker) {

		return null;
	}
}
