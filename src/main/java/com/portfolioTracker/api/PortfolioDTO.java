package com.portfolioTracker.api;

import org.springframework.stereotype.Component;

/**
 * PortfolioDTO is meant to minimize the need to make multiple @RequestParam
 * requests inside a single method. 
 * @author Georgios Davakos
 * @since 2020-11-26
 */
@Component
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

	public String getTicker() {
		return ticker;
	}

	public String getSharesNum() {
		return sharesNum;
	}

	public String getBuyInPrice() {
		return buyInPrice;
	}
}
