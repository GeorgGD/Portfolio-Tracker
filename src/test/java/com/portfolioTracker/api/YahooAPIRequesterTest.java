package com.portfolioTracker.api;

import static org.junit.Assert.assertNull;

import org.junit.Test;

import okhttp3.OkHttpClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolioTracker.api.YahooAPIRequester;

public class YahooAPIRequesterTest {

	private YahooAPIRequester initialize() {
		YahooAPIRequester api = new YahooAPIRequester();
		api.setClient(new OkHttpClient());
		api.setMapper(new ObjectMapper());
		return api;
	}
	
	@Test
	public void currentStockData() {
		YahooAPIRequester api = initialize();
		String actualString = api.currentStcokData("");
		assertNull(actualString);
	}
}
