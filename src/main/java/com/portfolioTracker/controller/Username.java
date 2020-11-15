package com.portfolioTracker.controller;

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
		//If username is null return to index
		if(username == null || username.equals("")) {
			
		}
		//Else create a cookie with username and send them to portfolio page
		return null;
	}
}
