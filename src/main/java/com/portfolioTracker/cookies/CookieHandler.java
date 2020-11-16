package com.portfolioTracker.cookies;

import javax.servlet.http.Cookie;

import org.springframework.stereotype.Component;

@Component
public class CookieHandler {

	private Cookie cookie;

	public void setCookie(Cookie cookie) {
		this.cookie = cookie;
	}

	public Cookie getCookie() {
		return cookie;
	}

	public void addAge(int age) {
		if(cookie != null) {
			cookie.setMaxAge(age);
		}
	}

	public void oneWeekCookie() {
		if(cookie != null) {
			cookie.setMaxAge(60*60*24*7);
		}
	}
}
