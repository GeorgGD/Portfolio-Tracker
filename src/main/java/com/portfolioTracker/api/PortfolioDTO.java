package com.portfolioTracker.api;

public class PortfolioDTO {
	private String ticker;
	private String sharesNum;
	private String buyInPrice;

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

   	public void setSharesNum(String sharesNum) {
		this.sharesNum = sharesNum;
	}

	public void setBuyInPrice(String buyInPrice) {
		this.buyInPrice = buyInPrice;
	}
	
}
