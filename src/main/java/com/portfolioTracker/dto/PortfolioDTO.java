package com.portfolioTracker.dto;

import javax.validation.constraints.NotBlank;

/**
 * PortfolioDTO is meant to minimize the need to make multiple @RequestParam
 * requests inside a single method. 
 * @author Georgios Davakos
 * @since 2020-11-26
 */
public class PortfolioDTO {
	@NotBlank(message = "Please provide a ticker")
	private String ticker;

	@NotBlank(message = "Please provide number of shares")
	private String sharesNum;

	@NotBlank(message = "Please provide the average cost per share")
	private String buyInPrice;

	/**
	 * Set the ticker of a stock
	 * @param ticker The ticker symbol of a stock	
	 */
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	/**
	 * Sets the number of shares
	 * @param sharesNum The number of shares	
	 */
   	public void setSharesNum(String sharesNum) {
		this.sharesNum = sharesNum;
	}

	/**
	 * Sets the average price of all the stocks
	 * @param buyInPrice The average price	
	 */
	public void setBuyInPrice(String buyInPrice) {
		this.buyInPrice = buyInPrice;
	}

	/**
	 * Retrieves the ticker symbol
	 * @return The ticker	
	 */
	public String getTicker() {
		return ticker;
	}

	/**
	 * Retrieves the number of shares
	 * @return The number of shares	
	 */
	public String getSharesNum() {
		return sharesNum;
	}

	/**
	 * Retrieves the average price
	 * @return The average price	
	 */
	public String getBuyInPrice() {
		return buyInPrice;
	}
}
