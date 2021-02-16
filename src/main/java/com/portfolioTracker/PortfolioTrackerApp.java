package com.portfolioTracker;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import okhttp3.OkHttpClient;

/**
 * The main class for the spring boot application
 * @author Georgios Davakos
 * @since 2021-02-16
 */

@SpringBootApplication
public class PortfolioTrackerApp {

	public static void main(String[] args) {
		SpringApplication.run(PortfolioTrackerApp.class, args);
	}

	@Bean
	public OkHttpClient client() {
		return new OkHttpClient();
	}

	@Bean
	public ObjectMapper mapper() {
		return new ObjectMapper();
	}
}
