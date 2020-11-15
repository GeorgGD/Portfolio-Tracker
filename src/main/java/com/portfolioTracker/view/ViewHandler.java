package com.portfolioTracker.view;

import java.util.ArrayList;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

/**
 * ViewHandler is meant to manage the view object in a MVC project
 * @author Georgios Davakos
 * @since 2020-11-15
 */
@Component
public class ViewHandler {
	
	private ModelAndView mav;

	public void setModelView(ModelAndView mav) {
		this.mav = mav;
	}

	public ModelAndView getModelView() {
		return mav;
	}
	
	/**
	 * Sets up the view object with the data it needs
	 * @param expressions The EL expressions found in the view 
	 * @param values The values the EL expressions are meant to hold
	 * @return the modefied model and view	
	 */
	public ModelAndView setupModelAndView(ArrayList<String> expressions, ArrayList<String> values, String view) {
		// TODO: Combine expressions variable with values into a map because this solution isn't scalable!
		mav.setViewName(view);

		int size = values.size();
		for (int i = 0; i < size; ++i) {
			mav.addObject(expressions.get(i), values.get(i));
		}

		return mav;
	}
}
