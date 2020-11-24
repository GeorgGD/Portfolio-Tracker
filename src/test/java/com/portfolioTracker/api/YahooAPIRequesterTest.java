package com.portfolioTracker.api;

import static org.junit.Assert.*;

import org.junit.Test;

import okhttp3.OkHttpClient;

import com.fasterxml.jackson.databind.ObjectMapper;

public class YahooAPIRequesterTest {

	private YahooAPIRequester initialize() {
		YahooAPIRequester api = new YahooAPIRequester();
		api.setClient(new OkHttpClient());
		api.setMapper(new ObjectMapper());
		return api;
	}
	
	@Test
	public void currentStockDataTest() {
		YahooAPIRequester api = initialize();
		String actualString = api.currentStockData("");
		assertNull(actualString);
	}

	@Test
	public void currentStockDataWithInput() {
		YahooAPIRequester api = initialize();
		String actualString = api.currentStockData("MSFT");

		assertNotNull(actualString);		
		assertNotEquals("", actualString);
	}

	@Test
	public void nameOfCompanyTest() throws TickerNotFoundException {
		YahooAPIRequester api = initialize();
		String actualString = api.nameOfCompany("MSFT");
		String expectedString = "Microsoft Corporation";
		assertEquals(expectedString,actualString);

		String actualString2 = api.nameOfCompany("LILA");
		String expectedString2 = "Liberty Latin America Ltd.";
		assertEquals(expectedString2, actualString2);
	}
	
	@Test
	public void currentPriceTest() {
		YahooAPIRequester api = initialize();
		double actualValue = api.currentPrice("LILA");
		assertTrue(actualValue > 0);
	}

	@Test
	public void currentPriceTestNoTicker() {
		YahooAPIRequester api = initialize();
		double actualValue = api.currentPrice("");
		double expected = -1;
		assertEquals(expected, actualValue, 1);
	}	
}
