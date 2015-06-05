package com.scb.wb.document.token;

import java.util.HashMap;
import java.util.Map;

public class TokenManager {

	private static final Map<String, String> token = new HashMap<String, String>();

	public void tokenHandler(final String consumerKey, final String consumerSecret, final String bankId) {
		// TODO: Fix Me! Temp Implementation.
		token.put("123456", "1234567890");
	}

	/**
	 * @return the token
	 */
	public Map<String, String> getToken() {
		return token;
	}
}
