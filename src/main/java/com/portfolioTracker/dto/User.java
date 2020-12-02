package com.portfolioTracker.dto;

import javax.validation.constraints.NotBlank;

/**
 * The following class is meant to store the user info into a POJO 
 * to simplify login validation
 * @author Georgios Davakos
 * @since 2020-12-01
 */
public class User {

	@NotBlank(message = "Username may not be blanc!")
	private String username;

	@NotBlank(message = "Password may not be blanc!")
	private String password;

	/**
	 * Gets username value
	 * @return The username	
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the value for username
	 * @param username The username value	
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Retrieves the password
	 * @return The password	
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the value for password
	 * @param password The password value	
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	
}
