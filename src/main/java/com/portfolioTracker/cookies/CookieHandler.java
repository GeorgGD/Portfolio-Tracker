package com.portfolioTracker.cookies;

import javax.servlet.http.Cookie;

import org.springframework.stereotype.Component;

/**
 * The following class is meant to manage cookies
 * @author Georgios Davakos
 * @since 2020-11-16
 */
@Component
public class CookieHandler {

	private Cookie cookie;
	
	public void setCookie(Cookie cookie) {
		this.cookie = cookie;
	}

	public Cookie getCookie() {
		return cookie;
	}

	/**
	 * Sets the expire time of the cookie
	 * @param age The max age	
	 */
	public void addAge(int age) {
		if(cookie != null) {
			cookie.setMaxAge(age);
		}
	}

	/**
	 * Sets the expire time to one week	
	 */
	public void oneWeekCookie() {
		if(cookie != null) {
			cookie.setMaxAge(60*60*24*7);
		}
	}

	public Cookie putInsideCookie(String key, String value) {
		return new Cookie(key, value);
	}
}
