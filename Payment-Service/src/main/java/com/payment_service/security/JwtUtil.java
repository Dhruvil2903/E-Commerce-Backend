package com.payment_service.security;

import io.jsonwebtoken.Jwts;

public class JwtUtil {

	private String key = "PU/BIMsbouuT5TSKHQ6FsJCZM77jJqxjcpNnHVr6CQErtjFIKiaunbI4oCV6USLzrsX8lQ89UmrX+zyL1thKZA==";
	
	public String validateToken(String token) {

		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
	}
}
