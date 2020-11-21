package com.portfolioTracker.view;

import java.util.ArrayList;
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

	public String setupTableEntries(ArrayList<String> entries) {
		if(entries == null || entries.size() == 0)
			return "";

		int size = entries.size();
		String rowStart = "<tr>";
		String rowEnd = "</tr>";
		String rowElements = "";
		
		for (int i = 0; i < size; ++i) {
			rowElements = rowElements + "<td>" + entries.get(i) + "</td>";
		}
		
		return rowStart + rowElements + rowEnd;
	}
}
