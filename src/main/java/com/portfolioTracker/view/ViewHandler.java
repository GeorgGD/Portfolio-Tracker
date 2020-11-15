package com.portfolioTracker.view;

import java.util.ArrayList;

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

	/** //TODO: REDO DOCS
	 * A helper function for tickerSeearch-method, sets up the model and view
	 * @param mav The model and view
	 * @param values The string that the model and view should hold
	 * @return the modefied model and view	
	 */
	public ModelAndView setupModelAndView(ArrayList<String> expressions, ArrayList<String> values, String view) {
		mav.setViewName(view);

		int size = values.size();
		for (int i = 0; i < size; ++i) {
			mav.addObject(expressions.get(i), values.get(i));
		}

		return mav;
	}
}
