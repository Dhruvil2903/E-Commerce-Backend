package com.user.Security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.user.UserModel.User;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {
	@Value("${jwt.secret}")
	private String secretKeyString;

	private SecretKey SECRET;

	private final long expirationMs = 1000 * 60 * 60 * 2;
	@PostConstruct
	public void init() {
		this.SECRET = Keys.hmacShaKeyFor(secretKeyString.getBytes(StandardCharsets.UTF_8));
	}


	public String generateToken(User user) {

		return Jwts.builder().setSubject(user.getUsername()).claim("Roles", user.getRole()).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expirationMs)).signWith(SECRET) // no algorithm arg
																										// needed: key
																										// knows it
				.compact();
	}

	// same extractUsername/validateToken as before, but use key instead of SECRET
	public String extractUsername(String token) {
		return Jwts.parserBuilder().setSigningKey(SECRET).build().parseClaimsJws(token).getBody() // get token body
				.getSubject();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(SECRET).build().parseClaimsJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}
}