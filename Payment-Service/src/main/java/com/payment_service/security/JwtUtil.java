package com.payment_service.security;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {

	@Value("${jwt.secret}")
	private String secretKeyString;
	
	 private SecretKey getSigningKey() {
	        return Keys.hmacShaKeyFor(secretKeyString.getBytes(StandardCharsets.UTF_8));
	    }

	    public String validateToken(String token) {
	        return Jwts.parserBuilder()
	                .setSigningKey(getSigningKey())
	                .build()
	                .parseClaimsJws(token)
	                .getBody()
	                .getSubject();
	    }
	
	
}
