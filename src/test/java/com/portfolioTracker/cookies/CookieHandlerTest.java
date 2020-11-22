package com.portfolioTracker.cookies;

import static org.junit.Assert.*;

import javax.servlet.http.Cookie;

import org.junit.Test;

public class CookieHandlerTest {

	@Test
	public void setterGetterTest() {
		CookieHandler handler = new CookieHandler();
		String expectedKey = "test";
		String expectedValue = "success";
		Cookie cookie = handler.getCookie();		
		assertNull(cookie);
		
		handler.setCookie(new Cookie(expectedKey, expectedValue));

		Cookie cookie2 = handler.getCookie();		
		assertEquals(expectedKey, cookie2.getName());
		assertEquals(expectedValue, cookie2.getValue());
	}

	@Test
	public void addAgeTest() {
		CookieHandler handler = new CookieHandler();
		String expectedKey = "test";
		String expectedValue = "success";
		handler.setCookie(new Cookie(expectedKey, expectedValue));
		int expectedInt = 100;
		handler.addAge(expectedInt);
		
		Cookie cookie = handler.getCookie();
		assertEquals(expectedInt, cookie.getMaxAge());		
	}

	@Test
	public void oneWeekCookieTest() {
		CookieHandler handler = new CookieHandler();
		String expectedKey = "test";
		String expectedValue = "success";
		handler.setCookie(new Cookie(expectedKey, expectedValue));
		handler.oneWeekCookie();

		Cookie cookie = handler.getCookie();
		int expectedAge = 60*60*24*7;
		assertEquals(expectedAge, cookie.getMaxAge());
	}
}
