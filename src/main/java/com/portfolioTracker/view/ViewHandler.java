package com.portfolioTracker.view;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
public class ViewHandler {

	private ModelAndView mav;

	public void setModelView(ModelAndView mav) {
		this.mav = mav;
	}

	public ModelAndView getModelView() {
		return mav;
	}
}
