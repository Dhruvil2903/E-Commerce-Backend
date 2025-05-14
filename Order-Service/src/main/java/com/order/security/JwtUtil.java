package com.order.security;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
    private String secretKeyString;

    private SecretKey SECRET;

    @PostConstruct
    public void init() {
        this.SECRET = Keys.hmacShaKeyFor(secretKeyString.getBytes(StandardCharsets.UTF_8));
    }

	public String extractUser(String token) {
		return Jwts.parserBuilder().setSigningKey(SECRET).build().parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(SECRET).build().parseClaimsJws(token).getBody().getSubject();
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			e.printStackTrace();
			return false;
		}
	}

	public String extractUsername(String token) {
		return Jwts.parserBuilder().setSigningKey(SECRET).build().parseClaimsJws(token).getBody().getSubject();
	}

	public String extractRole(String token) {

		return Jwts.parserBuilder().setSigningKey(SECRET).build().parseClaimsJws(token).getBody().get("roles",
				String.class);
	}
}