package com.portfolioTracker.serverCom;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
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
		String portfolio = null;
		ObjectMapper mapper = new ObjectMapper();
			
		if (portfolio != null) {
			try {
				JsonNode jnsonTree = mapper.readTree(portfolio);
			} catch (JsonProcessingException e) {
				e.printStackTrace();				
			}
		} else {
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
			
			portfolio = stocks.toString();
			
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
}
