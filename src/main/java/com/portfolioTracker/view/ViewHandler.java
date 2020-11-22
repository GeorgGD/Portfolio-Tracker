package com.portfolioTracker.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
	 * @param expValuePair The EL expression paired with a value 
	 * @return the modefied model and view	
	 */
	public ModelAndView setupModelAndView(HashMap<String,String> expValuePair, String view) {	    
		mav.setViewName(view);		
		Set<Map.Entry<String, String>> pair = expValuePair.entrySet();
		
		for (Map.Entry<String, String> e: pair) {
			mav.addObject(e.getKey(), e.getValue());		   
		}

		return mav;
	}

	/**
	 * Adds the given name and value pair into the model
	 * @param name The name of the attribute
	 * @param value The value of the attribute	
	 */
	public void addObjectsToView(String name, String value) {
		mav.addObject(name, value);
	}

	/**
	 * Sets the view
	 * @param view The view	
	 */
	public void setView(String view) {
		mav.setViewName(view);
	}
}
