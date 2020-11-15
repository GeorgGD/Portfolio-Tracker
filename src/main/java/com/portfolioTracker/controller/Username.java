package com.portfolioTracker.controller;

import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.portfolioTracker.view.ViewHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Username {

	@Autowired
	private ViewHandler viewHandler;
	
	@RequestMapping(value = "/username", method = RequestMethod.POST)
	public ModelAndView setUsernameCookie(@RequestParam("userName") String username, HttpServletResponse response) {
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
		
		//Else create a cookie with username and send them to portfolio page
		Cookie cookie = new Cookie("username",username);
		response.addCookie(cookie);
		view = "portfolio";
		jspExpression = "currentInvestment";
		expValuePair.put(jspExpression, "0");

		jspExpression = "currentEvaluation";
		expValuePair.put(jspExpression, "0");

		mav = viewHandler.setupModelAndView(expValuePair, view);
		return mav;
	}
}
