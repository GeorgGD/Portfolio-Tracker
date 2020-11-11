package com.portfolioTracker.api;

public interface APIRequester {

	public String currentPrice(String ticker);

	public String stocksName(String ticker);
}
