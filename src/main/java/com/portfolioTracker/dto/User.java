package com.portfolioTracker.dto;


/**
 * The following class is meant to store the user info into a POJO 
 * to simplify login validation
 * @author Georgios Davakos
 * @since 2020-12-01
 */
public class User {

	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
}
