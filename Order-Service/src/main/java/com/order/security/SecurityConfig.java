package com.order.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private JwtOrderFilter jwtOrderFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				// disable CSRF since we’re using tokens
				.csrf(csrf -> csrf.disable())

				// no sessions; JWT tokens only
				.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				// authorize endpoints
				.authorizeHttpRequests(auth -> auth.requestMatchers("/api/orders/**").permitAll() // login, register
						.anyRequest().authenticated() // all others need auth
				)

				// register our JWT filter
				.addFilterBefore(jwtOrderFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
