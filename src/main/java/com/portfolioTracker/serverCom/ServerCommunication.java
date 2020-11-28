package com.portfolioTracker.serverCom;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
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

	@Autowired
	private ObjectMapper mapper;

	// Constants
	private final String STOCKS_NAME = "name";
	private final String CURRENTLY_INVESTED = "invested";
	private final String CURRENTLY_WORTH = "worth";
	private final String STOCKS_ARRAY = "stocks";
	private final String NUMBER_SHARES = "shares";
	private final String BUY_IN_PRICE = "buyInPrice";

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
	 * Sets the API manager
	 * @param api The API manager
	 */
	public void setAPIRequester(APIRequester api) {
		this.api = api;
	}

	/**
	 * Sets the JSON mapper
	 * @param mapper The JSON mapper
	 */
	public void setObjectMapper(ObjectMapper mapper) {
		this.mapper = mapper;
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
	 * @param stockData The data about the stock
	 * @return The first entry in json form	
	 */
	private String createFirstEntry(String ticker, HashMap<String,String> stockData) {
    	ObjectNode stocks = mapper.createObjectNode();
		ArrayNode stockArray = mapper.createArrayNode();
		stockArray.add(ticker);
		stocks.set(STOCKS_ARRAY, stockArray);
		
	    ObjectNode tickerNode = createNewObject(stockData);
		
		stocks.set(ticker, tickerNode);
		stocks.put(CURRENTLY_INVESTED, 0.0);
		stocks.put(CURRENTLY_WORTH, 0.0);
	   return stocks.toString();		
	}
	
	/**
	 * Helper function to addStockToPortfolio
	 * Check addStockToPortfolio for docs! 	
	 */
	private String addStockToPortfolioAux(String username, String ticker, HashMap<String, String> stockData) {
		String portfolio = readPortfolio(username);
		if(portfolio.equals("")) {
			portfolio = createFirstEntry(ticker, stockData);		    
			return portfolio;
		}
			
		
		try {
			ObjectNode rootNode = (ObjectNode) mapper.readTree(portfolio);
			ArrayNode stocks = (ArrayNode) rootNode.get(STOCKS_ARRAY);

			if(!tickerExists(ticker, stocks))
				stocks.add(ticker);

			ObjectNode tickerNode = createNewObject(stockData);
			
			rootNode.set(ticker, tickerNode);
			portfolio = rootNode.toString();
		} catch (JsonProcessingException e) {
			e.printStackTrace();				
		}
		return portfolio;
	}

	/**
	 * Creates a new JSON object
	 * @param stockData The data about the stock
	 * @return The JSON object	
	 */	
	private ObjectNode createNewObject(HashMap<String,String> stockData) {
		ObjectNode tickerNode = mapper.createObjectNode();
		
		Set<Map.Entry<String, String>> pair = stockData.entrySet();
		for(Map.Entry<String, String> e: pair) {
			if(!e.getKey().equals("currentInvestment") && !e.getKey().equals("currentEvaluation"))
				tickerNode.put(e.getKey(), e.getValue());
		}
		return tickerNode;
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
			return "0";
;
		try {
			JsonNode jsonTree = mapper.readTree(portfolio);
			ArrayNode stocks = (ArrayNode) jsonTree.get(STOCKS_ARRAY);

		    for(JsonNode node : stocks) {
				shares = jsonTree.get(node.asText()).get(NUMBER_SHARES).asDouble();
				price = jsonTree.get(node.asText()).get(BUY_IN_PRICE).asDouble();
				total = total + shares * price;
			}
			return String.valueOf(oneDecimal(total));
			
		} catch (JsonProcessingException e) {		    
			e.printStackTrace();
		}
		return "0";
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
			return "0";

    	try {
			JsonNode jsonTree = mapper.readTree(portfolio);
			ArrayNode stocks = (ArrayNode) jsonTree.get(STOCKS_ARRAY);

			for(JsonNode node : stocks) {
				shares = jsonTree.get(node.asText()).get(NUMBER_SHARES).asDouble();
				price = api.currentPrice(node.asText());
				total = total + price * shares;
			}
			return String.valueOf(oneDecimal(total));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return "0";
	}

	/**
	 * Sets up the portfolio contents of the given user
	 * in HTML table format
	 * @param username The name of the user
	 * @return The table body with all the content	
	 */	
	public String setupTableEntries(String username) {
		if(username == null || username.equals(""))
			return defaultTable();
	
		String portfolio = readPortfolio(username);
		
		if(portfolio.equals(""))
			return defaultTable();
		
    	String tableBody = "";
		ArrayList<String> contentArr = new ArrayList<String>();
		
		try {
			JsonNode jsonTree = mapper.readTree(portfolio);
			ArrayNode stocks = (ArrayNode) jsonTree.get(STOCKS_ARRAY);
	
			for(JsonNode node : stocks) {
				contentArr.add(jsonTree.get(node.asText()).get(STOCKS_NAME).asText());
				contentArr.add(jsonTree.get(node.asText()).get(NUMBER_SHARES).asText());
				contentArr.add(jsonTree.get(node.asText()).get(BUY_IN_PRICE).asText() + " USD");
				tableBody = tableBody + setupTableEntries(contentArr);
				contentArr.clear();
			}
			return tableBody;
		} catch (JsonMappingException e) {		    
			e.printStackTrace();
			return defaultTable();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return defaultTable();
		}
	}
	
	/**
	 * Returns a single HTML table row with three rows
	 * Will be deprecated in the future!	
	 */
	private String defaultTable() {
		return "<tr><td></td><td></td><td></td></tr>";
	}

	/**
	 * Sets up the entries into a HTML table format 
	 * @param entries The row entries for the table
	 * @return The table row with the given entries	
	 */
	private String setupTableEntries(ArrayList<String> entries) {
		if(entries == null || entries.size() == 0)
			return "";
		
		int size = entries.size();
		String rowStart = "<tr>";
		String rowEnd = "</tr>";
		String rowElements = "";
		
		for (int i = 0; i < size; ++i) {
			rowElements = rowElements + "<td>" + entries.get(i) + "</td>";
		}
		
		return rowStart + rowElements + rowEnd;
	}

	/**
	 * Rounds out the value to one decimal
	 * @param value The value
	 * @return The rounded out value	
	 */
	private double oneDecimal(double value) {
		value = value * 10;
		value = Math.floor(value);
		value = value / 10;		
		return value;
	}
}
