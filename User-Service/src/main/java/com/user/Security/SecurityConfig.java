package com.user.Security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.user.Security.JwtAuthenticationFilter;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				// disable CSRF since we’re using tokens
				.csrf(csrf -> csrf.disable())

				// no sessions; JWT tokens only
				.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				// authorize endpoints
				.authorizeHttpRequests(auth -> auth.requestMatchers("/api/auth/**").permitAll() // login, register
						.anyRequest().authenticated() // all others need auth
				)

				// register our JWT filter
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
	 @Bean
	    public CorsConfigurationSource corsConfigurationSource() {
	        CorsConfiguration config = new CorsConfiguration();
	        config.setAllowedOrigins(List.of("http:/localhost:5173"));           // or restrict to your UI origin
	        config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
	        config.setAllowedHeaders(List.of("*"));
	        UrlBasedCorsConfigurationSource src = new UrlBasedCorsConfigurationSource();
	        src.registerCorsConfiguration("/**", config);
	        return src;
	    }
}
