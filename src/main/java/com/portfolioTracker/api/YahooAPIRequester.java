package com.portfolioTracker.api;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Component;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A class for making API requests to the Yahoo Finance API
 * @author Georgios Davakos
 * @since 2020-11-13 
 */
@Component
public class YahooAPIRequester implements APIRequester {	

	private static OkHttpClient client = new OkHttpClient(); //TODO: add a close method

	/**
	 * Closes the client used for api calls
	 */	
	public void closeClient() {
		try {
			if(client != null)
				client.cache().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Looks up the current price of a stock for a given ticker symbol
	 * @param ticker The ticker of the stock
	 * @return The name and current price of a stock
	 */
	public String currentStcokData(final String ticker) {		
		final Request request = prepRequest(ticker);
		
		Response response = null;
		String responseString = null;
		try {
			response = client.newCall(request).execute();
		   
			if (response.isSuccessful()) {
				ObjectMapper parser = new ObjectMapper();
				JsonNode jsonTree = parser.readTree(response.body().string());	
				responseString = selectStockName(jsonTree);
				responseString = responseString + ": " + selectCurrPrice(jsonTree);
				responseString = responseString + " " + selectCurrency(jsonTree);
			}
			return responseString;
		} catch (IOException e) {
			e.printStackTrace();
			
		} finally {
			if(response != null)
				response.close();			
		}
		return responseString;
	}

	/**
	 * Provides the name of a company for the given ticker
	 * @param ticker The ticker of the company
	 * @return The name of the company	
	 */	
	public String nameOfCompany(final String ticker) {
		final Request request = prepRequest(ticker);

		Response response = null;
		String responseString = null;
		try{
			response = client.newCall(request).execute();
			if(response.isSuccessful()) {
				ObjectMapper parser = new ObjectMapper();
				JsonNode jsonTree = parser.readTree(response.body().string());
				responseString = selectStockName(jsonTree);
			}
			
			return responseString;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(response != null)
				response.close();
		}
		
		return responseString;
	}

	/**
	 * The current price per share in USD
	 * @param ticker The ticker of the company
	 * @return The current price of the company	
	 */	
	public int currentPrice(final String ticker) {
		final Request request = prepRequest(ticker);

		Response response = null;
		int responseValue = -1;
		try{
			response = client.newCall(request).execute();
			if(response.isSuccessful()) {
				ObjectMapper parser = new ObjectMapper();
				JsonNode jsonTree = parser.readTree(response.body().string());
				String currPrice = selectCurrPrice(jsonTree);
				responseValue = Integer.parseInt(currPrice);
			}

			return responseValue;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(response != null)
				response.close();
		}

		return responseValue;
	}
	
	/**
	 * Prepers the request to the API
	 * @param ticker The ticker of the company
	 * @return The prepered request object	
	 */	
	private final Request prepRequest(String ticker) {
		final Request request = new Request.Builder()
			.url("https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/v2/get-analysis?symbol=" + ticker + "&region=US")
			.get()
			.addHeader("x-rapidapi-key", "f66deea662msh14939375d70d6c0p1886d9jsn758536cc2677")
			.addHeader("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
			.build();
	
		return request;
	}
	
	/**
	 * Selects the current price 
	 * @param jsonTree A JSON object represented as a tree
	 * @return The current price	
	 */	
	private String selectCurrPrice(JsonNode jsonTree) {
		String[] valueArr = { "financialData", "currentPrice", "fmt" };
		JsonNode node = selectNode(jsonTree, valueArr);

		if(node != null) {
			String price = node.asText();
			return price;	    
		}
		
		return null;
	}

	/**
	 * Select a JSON object represented as a node
	 * @param jsonTree A JSON object represented as a tree
	 * @param valueArr Array with names to JSON objects 
	 * @return The JSON object as a node	
	 */	
	private JsonNode selectNode(JsonNode jsonTree, String[] valueArr) {
		if (jsonTree != null) {
			try {
				int length = valueArr.length;
				JsonNode node = jsonTree;

				for (int i = 0; i < length; ++i) {
					node = node.get(valueArr[i]);
					
				}

				return node;
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * Selects the name of the stock
	 * @param jsonTree A JSON object represented as a tree
	 * @return The name of the stock	
	 */
	private String selectStockName(JsonNode jsonTree) {
		String[] valueArr = { "price", "shortName" };
		JsonNode node = selectNode(jsonTree, valueArr);

		if(node != null) {
			String name = node.asText();
			return name;
		}
		return null;
	}

	/**
	 * Selects the node that holds the corrency value
	 * @param jsonTree A JSON object represented as a tree
	 * @return The currency 	
	 */
	private String selectCurrency(JsonNode jsonTree) {
		String[] valueArr = { "financialData", "financialCurrency" };
		JsonNode node = selectNode(jsonTree, valueArr);

		if(node != null) {
			String currency = node.asText();
			return currency;
		}
		
		return null;
	}
}
