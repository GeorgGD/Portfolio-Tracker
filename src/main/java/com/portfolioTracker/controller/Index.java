package com.portfolioTracker.controller;

import com.portfolioTracker.dto.User;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The following controller is meant to handling the view and data for the landing page
 * @author Georgios Davakos
 * @since 2020-11-29
 */
@Controller
public class Index {

	/**
	 * Provides the view for when the visitor goes to the landing page
	 * @return The view
	 */
	@RequestMapping("/")
	public String landingPage(@ModelAttribute("userInfo") User user) {
		return "index";
	}
}
