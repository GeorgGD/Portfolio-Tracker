package com.portfolioTracker.api;

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
	 * @return The name and current price of the company
	 */
	public String currentStcokData(final String ticker);

	/**
	 * Closes the client used for api calls
	 */	
	public void closeClient();

	/**
	 * Provides the name of a company for the given ticker
	 * @param ticker The ticker of the company
	 * @return The name of the company	
	 */	
	public String nameOfCompany(final String ticker);

	
	/**
	 * The current price per share in USD
	 * @param ticker The ticker of the company
	 * @return The current price of the company	
	 */	
	public int currentPrice(final String ticker);
	
}
