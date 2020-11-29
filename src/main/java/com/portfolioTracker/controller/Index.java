package com.portfolioTracker.controller;

import com.portfolioTracker.view.ViewHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Index {

	@Autowired
	private ViewHandler viewHandler;

	@RequestMapping("/")
	public ModelAndView landingPage() {
		viewHandler.newModelAndView();
		viewHandler.setView("index");
		return viewHandler.getModelView();
	}
}
