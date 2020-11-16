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
}
