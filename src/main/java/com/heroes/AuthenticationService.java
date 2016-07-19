package com.heroes;

import java.util.Base64;
import java.util.Base64.Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public enum AuthenticationService {
	
	INSTANCE;
	
	boolean checkAuthorization(String authString) {
		
		String[] tokens = authString.split("\\s+");
		
		Decoder decoder = Base64.getDecoder();
		
		byte[] decodedBytes = decoder.decode(tokens[1]);
		
		String decoded = new String(decodedBytes);
		
		String[] userPassTokens = decoded.split(":");
		
		String user = userPassTokens[0];
		String pass = userPassTokens[1];
		
		if ("admin".equals(user) && "1234".equals(pass)) {
			return true;
		}
		
		return false;
	}
	
	boolean sessionAuthorized(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Boolean authorized = (Boolean) session.getAttribute("authorized");
		
		return authorized != null && authorized;
	}

}
