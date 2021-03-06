package com.portfolioTracker.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.portfolioTracker.dto.PortfolioDTO;
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
	 * Used when trying to login into the portfolio account
	 * @param user The user trying to login
	 * @param result All the validation errors
	 * @param model The model from the request scope
	 * @param session The session from the session scope	
	 * @return The view	
	 */
	@RequestMapping(value = "/username", method = RequestMethod.POST, params = "login")
	public String loginUser(@Valid @ModelAttribute("userInfo") User user, BindingResult result, Model model, HttpSession session) {
		String username = user.getUsername();
	    
		if(result.hasErrors()) {		    
			return "index";
		}
		
		if(userAuth.loginSuccess(user)) {
			server.createUser(username);
			session.setAttribute("username", username);
			session.setMaxInactiveInterval(60*60*24*2);
			
			setupModel(username, model);
			
			return "portfolio";
		}
		
		model.addAttribute("errorMsg", "Login failed, Username and Password didn't match");
	    return "index";
	}

	/**
	 * Registers a new user into the datebase 
	 * @param user The user to register
	 * @param errorResult All the validation errors	
	 * @param model The model from the request scope
	 * @param session The session from the session scope
	 * @return The view	
	 */
	@RequestMapping(value = "/username", method = RequestMethod.POST, params = "register")
	public String registerUser(@Valid @ModelAttribute("userInfo") User user, BindingResult errorResult, Model model, HttpSession session) {
		String viewForErrors = "index";
		String viewForSuccess = "portfolio";
		
		if(errorResult.hasErrors()) {
			return viewForErrors;
		}

		if (userAuth.userIsRegistered(user)) {
			model.addAttribute("errorMsg", "Registration failed, username is taken");	    
			return viewForErrors;
		}

		if(userAuth.registerUser(user)) {
			session.setAttribute("username", user.getUsername());
			session.setMaxInactiveInterval(60*60*24*2);		

			setupModel(user.getUsername(), model);
			return viewForSuccess;
		}

		model.addAttribute("errorMsg", "Registration failed!");	    		
		return viewForErrors;
	}

	/**
	 * Provides data to a model 
	 * @param username The name of the user
	 * @param model The model from the request scope	
	 */
	private void setupModel(String username, Model model) {
		String jspExpression;
		String currency = " USD";		
		String currInvestment = server.checkCurrentInvestment(username);
		String currWorth = server.checkEvaluation(username);

		jspExpression = "currentInvestment";
		model.addAttribute(jspExpression, currInvestment + currency);
		
		jspExpression = "currentEvaluation";
		model.addAttribute(jspExpression, currWorth + currency);

		jspExpression = "tableBody";
	    model.addAttribute(jspExpression, server.setupTableEntries(username));
		model.addAttribute("portfolioData", new PortfolioDTO());
	}
}
