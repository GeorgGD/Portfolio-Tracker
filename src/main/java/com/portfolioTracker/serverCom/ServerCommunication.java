package com.portfolioTracker.serverCom;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.portfolioTracker.api.APIRequester;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * The following class is meant to communicate with a database and save data that
 * can't be stored inside a cookie
 * @author Georgios Davakos
 * @since 2020-11-19
 */
@Component
public class ServerCommunication {

	@Autowired
	private APIRequester api;
	
	/**
	 * Creates a user
	 * @param username The name of the user	
	 */ 
	public void createUser(String username) {
		try {			
			File user = new File(username + ".txt");
			user.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}									
	}

	/**
	 * Adds a stock to the portfolio of the given user
	 * @param username The user name of the user
	 * @param ticker The ticker of the stock
	 * @param stockData The data about the stock that will be stored	
	 */
	public void addStockToPortfolio(String username, String ticker, HashMap<String, String> stockData) {
		String portfolio;
		File user = new File(username + ".txt");
		if (user.exists()) {
			portfolio = addStockToPortfolioAux(username, ticker, stockData);
			saveChanges(username, portfolio);
		} else {
			portfolio = createFirstEntry(ticker, stockData);
			saveChanges(username, portfolio);
		}
	}

	/**
	 * Saves the data into a text file
	 * Will be depricated in the future
	 * @param username The user name of the user
	 * @param toSave The data you want to save 		
	 */
	private void saveChanges(String username, String toSave) {
		if(username.equals(""))
			return;
		
		File user = new File(username + ".txt");
		FileWriter toWrite = null;
		if(user.exists()) {
			try {
				toWrite = new FileWriter(username + ".txt", false);
				toWrite.write(toSave);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (toWrite != null) {
					try {
						toWrite.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			createUser(username);
			try {
				toWrite = new FileWriter(username + ".txt", false);
				toWrite.write(toSave);
			} catch (IOException e) {
		    	e.printStackTrace();
			} finally {
				if (toWrite != null) {
					try {
						toWrite.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}			
		}		
	}

	/**
	 * Creates the first entry for a new portfolio
	 * @param ticker The ticker of the stock
	 * @param stockData The data to be used for the first entry
	 * @return The first entry in json form	
	 */
	private String createFirstEntry(String ticker, HashMap<String,String> stockData) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode stocks = mapper.createObjectNode();
		ArrayNode stockArray = mapper.createArrayNode();
		stockArray.add(ticker);
		stocks.set("stocks", stockArray);
		
		ObjectNode tickerNode = mapper.createObjectNode();
		
		Set<Map.Entry<String, String>> pair = stockData.entrySet();
		for(Map.Entry<String, String> e: pair) {
			if(!e.getKey().equals("currentInvestment") && !e.getKey().equals("currentEvaluation"))
				tickerNode.put(e.getKey(), e.getValue());
		}
		stocks.set(ticker, tickerNode);
		
	   return stocks.toString();		
	}

	/**
	 * Helper function to addStockToPortfolio
	 * Check addStockToPortfolio for docs! 	
	 */
	private String addStockToPortfolioAux(String username, String ticker, HashMap<String, String> stockData) {
		String portfolio = readPortfolio(username);
		if(portfolio.equals(""))
			return "";
		
		ObjectMapper mapper = new ObjectMapper();		
		try {
			ObjectNode rootNode = (ObjectNode) mapper.readTree(portfolio);
			ArrayNode stocks = (ArrayNode) rootNode.get("stocks");

			if(!tickerExists(ticker, stocks))
				stocks.add(ticker);

			ObjectNode tickerNode = mapper.createObjectNode();

			Set<Map.Entry<String, String>> pair = stockData.entrySet();
			for(Map.Entry<String, String> e: pair) {
				if(!e.getKey().equals("currentInvestment") && !e.getKey().equals("currentEvaluation"))
					tickerNode.put(e.getKey(), e.getValue());
			}
			
			rootNode.set(ticker, tickerNode);
			portfolio = rootNode.toString();
		} catch (JsonProcessingException e) {
			e.printStackTrace();				
		}
		return portfolio;
	}

	/**
	 * Checks if a ticker already exists in the array of stocks
	 * @param ticker The ticker of the stock
	 * @param stocks The array with all the stocks
	 * @return true if the ticker already exists in the array	
	 */	
	private boolean tickerExists(String ticker, ArrayNode stocks) {
		for(JsonNode node : stocks) {
			if(node.asText().equals(ticker))			
				return true;;
		}
		return false;
	}
		
	/**
	 * Reads the portfolio of the given user
	 * @param username The name of the user
	 * @return The portfolio	
	 */
	private String readPortfolio(String username) {
		File file = new File(username + ".txt");
		String portfolio = "";
		Scanner scan = null;
		
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return "";
		}
		
		while(scan.hasNextLine()) {
			portfolio = portfolio + scan.nextLine();
		}
		
		if(scan != null)
			scan.close();
		return portfolio;
	}

	/**
	 * Provides the current investment of the given user
	 * @param username The name of the user
	 * @return The current investment	
	 */
	public String updateCurrentInvestment(String username) {
		double shares;
		double price;
		double total = 0; 
		String portfolio = readPortfolio(username);

		if(portfolio.equals(""))
			return "";

		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode jsonTree = mapper.readTree(portfolio);
			ArrayNode stocks = (ArrayNode) jsonTree.get("stocks");

		    for(JsonNode node : stocks) {
				shares = jsonTree.get(node.asText()).get("shares").asDouble();
				price = jsonTree.get(node.asText()).get("buyInPrice").asDouble();
				total = total + shares * price;
			}
			total = total * 10;
			total = Math.floor(total);
			total = total / 10;
			return String.valueOf(total);
			
		} catch (JsonProcessingException e) {		    
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Provides the current evaluation of the users portfolio
	 * @param username The name of the user
	 * @return The current evaluation	
	 */
	public String updateCurrentEval(String username) {
		double shares;
		double price;
		double total = 0;
		String portfolio = readPortfolio(username);

		if(portfolio.equals(""))
			return "";

		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode jsonTree = mapper.readTree(portfolio);
			ArrayNode stocks = (ArrayNode) jsonTree.get("stocks");

			for(JsonNode node : stocks) {
				shares = jsonTree.get(node.asText()).get("shares").asDouble();
				price = api.currentPrice(node.asText());
				total = total + price * shares;
			}
			total = total * 10;
			total = Math.floor(total);
			total = total / 10;
			return String.valueOf(total);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return "";
	}
}
