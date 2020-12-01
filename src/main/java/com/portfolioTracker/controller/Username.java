package com.portfolioTracker.controller;

import javax.servlet.http.HttpSession;

import com.portfolioTracker.dto.User;
import com.portfolioTracker.serverCom.ServerCommunication;
import com.portfolioTracker.view.ViewHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * The Username class takes care of assigning usernames and managing 
 * sessions related to the user. 
 * @auther Georgios Davakos
 * @since 2020-11-17
 */
@Controller
public class Username {
	
	@Autowired
	private ViewHandler viewHandler; 

	@Autowired
	private ServerCommunication server;
	/**
	 * Sets a cookie with the username that was provided
	 * @param username The desired username
	 * @param response The response object
	 * @return The view with the data to display	
	 */
	@RequestMapping(value = "/username", method = RequestMethod.POST, params = "login")
	public ModelAndView setUsername(@ModelAttribute("userInfo") User user, HttpSession session) {
		String username = user.getUsername();
		String jspExpression;
		String view;
		String currency = " USD";		
		String currInvestment = server.checkCurrentInvestment(username);
		String currWorth = server.checkEvaluation(username);
		viewHandler.newModelAndView();
		
		if(username == null || username.equals("")) {
			view = "index";
			viewHandler.setView(view);
			jspExpression = "errorMsg";
			viewHandler.addObjectsToView(jspExpression, "Please provide a Username!");		    
			return viewHandler.getModelView();
		}
		
		session.setAttribute("username", username);
		session.setMaxInactiveInterval(60*60*24*2);

		view = "portfolio";		
		viewHandler.setView(view);
		
		jspExpression = "currentInvestment";
		viewHandler.addObjectsToView(jspExpression, currInvestment + currency);
		
		jspExpression = "currentEvaluation";
		viewHandler.addObjectsToView(jspExpression, currWorth + currency);

		jspExpression = "tableBody";
		viewHandler.addObjectsToView(jspExpression, server.setupTableEntries(username));
		
	    return viewHandler.getModelView();
	}

	@RequestMapping(value = "/username", method = RequestMethod.POST, params = "register")
	public String registerUser() {
		return "index";
	}
}
