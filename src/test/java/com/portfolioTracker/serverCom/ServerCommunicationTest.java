package com.portfolioTracker.serverCom;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolioTracker.api.YahooAPIRequester;

import org.junit.After;
import org.junit.Test;

public class ServerCommunicationTest {
	
	private String FILE_PATH = "src/main/resources/portfolios/";
	
	private ServerCommunication initialize() {
		ServerCommunication server = new ServerCommunication();
		server.setAPIRequester(new YahooAPIRequester());
		server.setObjectMapper(new ObjectMapper());
		return server;
	}

	private String readPortfolio(String username) {
		File file = new File(FILE_PATH + username + ".txt");
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

	@After
	public void removeFiles() {
		String FILE_PATH = "src/main/resources/portfolios/";
		File file = new File(FILE_PATH + "unitTest.txt");
		file.delete();
	}
	
	@Test
	public void createUserTest() {
		ServerCommunication server = initialize();
		String expUser = "unitTest";
		server.createUser(expUser);

		File file = new File(FILE_PATH + expUser + ".txt");
		assertTrue(file.exists());
	}

	@Test
	public void addStockToPortfolioTest() {
		ServerCommunication server = initialize();
		HashMap<String,String> stockData = new HashMap<String,String>();
		String expTicker = "MSFT";	
		String expUser = "unitTest";
		String expectedResult = "{\"stocks\":[\"MSFT\"],\"invested\":100.0,\"worth\":0.0,\"MSFT\":{\"shares\":\"20\",\"buyInPrice\":\"5\"}}";
		
		server.createUser(expUser);
		stockData.put("shares", "20");
		stockData.put("buyInPrice", "5");		
		server.addStockToPortfolio(expUser, expTicker, stockData);
		
		String actualResult = readPortfolio(expUser);
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void checkCurrentInvestmentTest() {
		ServerCommunication server = initialize();
		HashMap<String,String> stockData = new HashMap<String,String>();
		String expTicker = "MSFT";	
		String expUser = "unitTest";
		
		server.createUser(expUser);
		stockData.put("shares", "20");
		stockData.put("buyInPrice", "5");		
		server.addStockToPortfolio(expUser, expTicker, stockData);

		String actualResult = server.checkCurrentInvestment(expUser);
		assertNotEquals("0", actualResult);
	}

	@Test
	public void setupTableEntries() {
		ServerCommunication server = initialize();
		HashMap<String,String> stockData = new HashMap<String,String>();
		String expTicker = "MSFT";	
		String expUser = "unitTest";
		String expectedResult = "<tr><td>Microsoft</td><td>20</td><td>5 USD</td></tr>";
		server.createUser(expUser);
		stockData.put("name", "Microsoft");
		stockData.put("shares", "20");
		stockData.put("buyInPrice", "5");		
		server.addStockToPortfolio(expUser, expTicker, stockData);

		String actualResult = server.setupTableEntries(expUser);
		assertEquals(expectedResult, actualResult);
	}
}
