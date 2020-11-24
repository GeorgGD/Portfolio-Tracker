package com.portfolioTracker.controller;

import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.portfolioTracker.cookies.CookieHandler;
import com.portfolioTracker.serverCom.ServerCommunication;
import com.portfolioTracker.view.ViewHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * The Username class takes care of assigning usernames and managing 
 * cookies related to the user
 * @auther Georgios Davakos
 * @since 2020-11-17
 */
@Controller
public class Username {
	
	@Autowired
	private ViewHandler viewHandler; 

	@Autowired
	private CookieHandler cookieHandler; 

	@Autowired
	private ServerCommunication server;
	/**
	 * Sets a cookie with the username that was provided
	 * @param username The desired username
	 * @param response The response object
	 * @return The view with the data to display	
	 */
	@RequestMapping(value = "/username", method = RequestMethod.POST)
	public ModelAndView setUsernameCookie(@RequestParam("userName") String username,
										  @CookieValue(value = "invested", defaultValue = "0") String currInvestment,
										  @CookieValue(value = "worth", defaultValue = "0") String currWorth,
										  HttpServletResponse response) {
	    HashMap<String, String> expValuePair = new HashMap<String, String>();
		String jspExpression;
		String view;
		ModelAndView mav;
		viewHandler.setModelView(new ModelAndView());
	    
		if(username == null || username.equals("")) {
			view = "index";
			jspExpression = "errorMsg";
			expValuePair.put(jspExpression, "Please provide a Username!");
			mav = viewHandler.setupModelAndView(expValuePair,view);
			return mav;
		}
		
    	cookieHandler.setCookie(new Cookie("username",username));
		cookieHandler.oneWeekCookie();
		response.addCookie(cookieHandler.getCookie());
		view = "portfolio";
		jspExpression = "currentInvestment";
		expValuePair.put(jspExpression, currInvestment+" USD");

		jspExpression = "currentEvaluation";
		expValuePair.put(jspExpression, currWorth+" USD");
		expValuePair.put("tableBody", server.setupTableEntries(username));
		mav = viewHandler.setupModelAndView(expValuePair, view);
		return mav;
	}
}
