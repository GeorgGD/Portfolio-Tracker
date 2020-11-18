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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.stereotype.Component;

@Component
public class ServerCommunication {

	public void createUser(String username) {
		try {			
			File user = new File(username + ".txt");
			user.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}									
	}
	
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

	private void saveChanges(String username, String toSave) {
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

	private String addStockToPortfolioAux(String username, String ticker, HashMap<String, String> stockData) {
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
		
		ObjectMapper mapper = new ObjectMapper();		
		try {
			ObjectNode rootNode = (ObjectNode) mapper.readTree(portfolio);
			ArrayNode stocks = (ArrayNode) rootNode.get("stocks");
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
}
