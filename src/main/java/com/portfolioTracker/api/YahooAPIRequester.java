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
	 * @return The currentPrice of a stock
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
		   
			if(response.isSuccessful()) 
				responseString = selectCurrPrice(response);			
			
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
	private String selectCurrPrice(Response response) {
		String[] valueArr = { "financialData", "currentPrice", "fmt" };
		JsonNode node = selectNode(response, valueArr);

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
	private JsonNode selectNode(Response response, String[] valueArr) {
		ObjectMapper parser = new ObjectMapper();
		try {
			JsonNode jsonTree = parser.readTree(response.body().string());
			int length = valueArr.length;
			JsonNode node = null;
			
			for(int i = 0; i < length; ++i) {
				node = jsonTree.get(valueArr[i]);
			}
	
			return node;
		} catch (IOException e) {		    
			e.printStackTrace();		    
		} catch (NullPointerException e) {
			e.printStackTrace();		    
		}
		
		return null;
	}
	
	/**
	 * The name of the stock with the given tocker symbol
	 * @param ticker The ticker of the stock
	 * @return The name of the stock	
	 */
	public String stocksName(final String ticker) {
		//send get request

		//select the desired data

		return null;
	}
}
