package com.payment_service.security;

import io.jsonwebtoken.Jwts;

public class JwtUtil {

	private String key = "";
	
	public String validateToken(String token) {

		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
	}
}
