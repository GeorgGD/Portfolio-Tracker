package com.portfolioTracker.view;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

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

	@Test
	public void newModelAndViewTest() {
		ViewHandler view = new ViewHandler();
		ModelAndView mav = view.getModelView();
		assertNull(mav);
		
		view.newModelAndView();
		ModelAndView mav2 = view.getModelView();
		assertNotNull(mav2);
	}

	@Test
	public void addObjectsToViewTest() {
		ViewHandler view = new ViewHandler();
		view.newModelAndView();
		String expName = "try";
		String expValue = "catch";
		view.addObjectsToView(expName, expValue);

		ModelAndView mav = view.getModelView();
		Map<String, Object> map = mav.getModel();
		assertEquals(expValue, map.get(expName));
	}

	@Test
	public void setViewTest() {
		ViewHandler view = new ViewHandler();
		view.newModelAndView();
		String expView = "index";
		view.setView(expView);

		ModelAndView mav = view.getModelView();
		assertEquals(expView, mav.getViewName());
	}

	@Test
	public void setupModelAndViewTest() {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		String expKey = "try";
		String expValue = "catch";
		hashMap.put(expKey, expValue);
		String expView = "index";

		ViewHandler view = new ViewHandler();
		view.newModelAndView();
		ModelAndView mav = view.setupModelAndView(hashMap, expView);
		assertEquals(expView, mav.getViewName());
		
		Map<String, Object> map = mav.getModel();
		assertEquals(expValue, map.get(expKey));
	}
}
