package com.portfolioTracker.api;

import static org.junit.Assert.assertNull;

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
}
