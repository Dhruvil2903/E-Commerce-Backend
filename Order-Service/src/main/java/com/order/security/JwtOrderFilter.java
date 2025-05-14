package com.order.security;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtOrderFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		System.out.println("🔐 JwtOrderFilter invoked");

		String authHeader = request.getHeader("Authorization");
		System.out.println("🔍 Authorization Header: " + authHeader);

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);
			System.out.println("🔑 Extracted Token: " + token);

			try {
				if (jwtUtil.validateToken(token)) {
					System.out.println("✅ Token is valid");

					String username = jwtUtil.extractUsername(token);
					String role = jwtUtil.extractRole(token);

					System.out.println("👤 Username: " + username);
					System.out.println("🎭 Role: " + role);

					List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));

					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
							username, null, authorities);

					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				} else {
					System.out.println("❌ Token is invalid");
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					return;
				}

			} catch (JwtException | IllegalArgumentException e) {
				System.out.println("❗ JWT Exception: " + e.getClass().getSimpleName());
				System.out.println("🧨 Error Message: " + e.getMessage());
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return;
			}

		} else {
			System.out.println("🚫 Authorization header missing or does not start with 'Bearer '");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		// If everything passed, continue to the controller
		filterChain.doFilter(request, response);
	}

}
