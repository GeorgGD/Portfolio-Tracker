package com.portfolioTracker.api;

import java.io.IOException;

import org.springframework.stereotype.Component;

/**
 * The following interface is meant to allow developers to switch the API
 * they are using in the background without affecting classes using  
 * APIRequester. 
 * @author Georgios Davakos
 * @since 2020-11-11
 */
@Component
public interface APIRequester {

	/**
	 * Looks up the name and current price of a stock for a given ticker symbol
	 * @param ticker The ticker of the stock
	 * @return The name and current price of a stock
	 */
	public String currentStcokData(final String ticker);

	/**
	 * Closes the client used for api calls
	 */	
	public void closeClient();
	
	/* This method will be reworked for the future!
	/**
	 * The name of the stock with the given tocker symbol
	 * @param ticker The ticker of the stock
	 * @return The name of the stock	
	 
	public String stocksName(String ticker);*/
}
