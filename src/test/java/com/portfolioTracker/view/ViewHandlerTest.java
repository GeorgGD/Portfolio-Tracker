package com.portfolioTracker.view;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

public class ViewHandlerTest {

	@Test
	public void setterGetterTest() {
		ViewHandler view = new ViewHandler();
		view.setModelView(new ModelAndView());
		ModelAndView mav = view.getModelView();

		assertNotNull(mav);
	}
}
