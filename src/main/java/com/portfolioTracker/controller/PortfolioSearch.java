package com.portfolioTracker.controller;

import com.portfolioTracker.cookies.CookieHandler;
import com.portfolioTracker.view.ViewHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PortfolioSearch {

	@Autowired
	private ViewHandler viewHandler;

	@Autowired
	private CookieHandler cookieHandler;

}
