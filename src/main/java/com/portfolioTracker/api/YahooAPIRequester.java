package com.portfolioTracker.api;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Component;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Component
public class YahooAPIRequester implements APIRequester {	

	private static OkHttpClient client = new OkHttpClient(); //TODO: add a close method
		
	/**
	 * Looks up the current price of a stock for a given ticker symbol
	 * @param ticker The ticker of the stock
	 * @return The name and current price of a stock
	 */
	public String currentPrice(final String ticker) {
		
		final Request request = new Request.Builder()
			.url("https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/v2/get-analysis?symbol=" + ticker + "&region=US")
			.get()
			.addHeader("x-rapidapi-key", "f66deea662msh14939375d70d6c0p1886d9jsn758536cc2677")
			.addHeader("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
			.build();
		
		Response response = null;
		String responseString = null;
		try {
			response = client.newCall(request).execute();
		   
			if (response.isSuccessful()) {
				ObjectMapper parser = new ObjectMapper();
				JsonNode jsonTree = parser.readTree(response.body().string());	
				responseString = selectStockName(jsonTree);
				responseString = responseString + ": ";
				responseString = selectCurrPrice(jsonTree);
				responseString = " " + selectCurrency(jsonTree);
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
	 * Selects the current price 
	 * @param response The response object from an api request
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
	 * @param response The response object from an api request
	 * @param valueArr Array with names to JSON objects 
	 * @return The JSON object as a node	
	 */	
	private JsonNode selectNode(JsonNode jsonTree, String[] valueArr) {
    	try {
			int length = valueArr.length;
			JsonNode node = null;
		
			for(int i = 0; i < length; ++i) {
				node = jsonTree.get(valueArr[i]);
			}
	
			return node;
		} catch (NullPointerException e) {
			e.printStackTrace();		    
		}
		
		return null;
	}
	
	/**
	 * Selects the name of the stock
	 * @param ticker The api response holding the stocks name
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
