package com.portfolioTracker.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.portfolioTracker.dto.User;
import com.portfolioTracker.serverCom.ServerCommunication;
import com.portfolioTracker.serverCom.UserAuth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * The Username class takes care of assigning usernames and managing 
 * sessions related to the user. 
 * @auther Georgios Davakos
 * @since 2020-11-17
 */
@Controller
public class Username {
	
	@Autowired
	private ServerCommunication server;

	@Autowired
	private UserAuth userAuth;
	
	/**
	 * Sets a cookie with the username that was provided
	 * @param username The desired username
	 * @param response The response object
	 * @return The view with the data to display	
	 */
	@RequestMapping(value = "/username", method = RequestMethod.POST, params = "login")
	public String setUsername(@Valid @ModelAttribute("userInfo") User user, BindingResult result,Model model, HttpSession session) {
		String username = user.getUsername();
		String jspExpression;
		String currency = " USD";		
		String currInvestment = server.checkCurrentInvestment(username);
		String currWorth = server.checkEvaluation(username);
	    
		if(result.hasErrors()) {		    
			return "index";
		}
		
		session.setAttribute("username", username);
		session.setMaxInactiveInterval(60*60*24*2);

		jspExpression = "currentInvestment";
		model.addAttribute(jspExpression, currInvestment + currency);
		
		jspExpression = "currentEvaluation";
		model.addAttribute(jspExpression, currWorth + currency);

		jspExpression = "tableBody";
	    model.addAttribute(jspExpression, server.setupTableEntries(username));
		
	    return "portfolio";
	}

	@RequestMapping(value = "/username", method = RequestMethod.POST, params = "register")
	public String registerUser() {
		return "index";
	}
}
