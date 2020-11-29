package com.portfolioTracker.controller;

import com.portfolioTracker.view.ViewHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * The following controller is meant to handling the view and data for the landing page
 * @author Georgios Davakos
 * @since 2020-11-29
 */
@Controller
public class Index {

	@Autowired
	private ViewHandler viewHandler;

	/**
	 * Provides the view for when the visitor goes to the landing page
	 * @return The view
	 */
	@RequestMapping("/")
	public ModelAndView landingPage() {
		viewHandler.newModelAndView();
		viewHandler.setView("index");
		return viewHandler.getModelView();
	}
}
