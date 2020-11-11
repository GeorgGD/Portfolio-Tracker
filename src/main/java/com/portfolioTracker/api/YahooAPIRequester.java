package com.portfolioTracker.api;

import org.springframework.stereotype.Component;

@Component
public class YahooAPIRequester implements APIRequester{

	/**
	 * Looks up the current price of a stock for a given ticker symbol
	 * @param ticker The ticker of the stock
	 * @return The currentPrice of a stock	
	 */
	public String currentPrice(String ticker) {
		// send get request

		// select the desired data
		
		return null;
	}

	/**
	 * The name of the stock with the given tocker symbol
	 * @param ticker The ticker of the stock
	 * @return The name of the stock	
	 */

	public String stocksName(String ticker) {
		//send get request

		//select the desired data

		return null;
	}
}
