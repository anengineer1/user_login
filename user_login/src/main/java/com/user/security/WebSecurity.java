package com.user.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * @author Francisco
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurity {
	
	private JwtAuthEntryPoint jwtAuthEntryPoint;

	public WebSecurity(JwtAuthEntryPoint jwtAuthEntryPoint) {
		this.jwtAuthEntryPoint = jwtAuthEntryPoint;
	}

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		/*
		 * 1. Se desactiva el uso de cookies 2. Se activa la configuración CORS con los
		 * valores por defecto 3. Se desactiva el filtro CSRF 4. Se indica que el login
		 * no requiere autenticación 5. Se indica que el resto de URLs esten securizadas
		 */
		 http
         .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
         .and()
         .cors()
         .and()
         .csrf().disable()
         .exceptionHandling()
         .authenticationEntryPoint(jwtAuthEntryPoint)
         .and()
         .authorizeHttpRequests((authz) -> authz
        		 // Grant access to the console
                 .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                 // Grant access the the welcome message 
                 .requestMatchers(HttpMethod.GET, "/").permitAll()
                 // Grant access to login and register
                 .requestMatchers(HttpMethod.POST, "/register").permitAll()
                 .requestMatchers(HttpMethod.POST, "/login").permitAll()
                 // Prevent the deletion of the admin
                 .requestMatchers(HttpMethod.DELETE, "/users/1").denyAll()
                 // Any other request must be vie an authenticated user
                 .anyRequest().authenticated()
                 .and()
                 .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class));
		 
		 return http.build();
	}
	
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration auth_config) throws Exception {
    	return auth_config.getAuthenticationManager();
    }
    
	@Bean
	PasswordEncoder PasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
    
    @Bean
    JWTAuthenticationFilter jwtAuthenticationFilter() {
    	return new JWTAuthenticationFilter();
    }

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
}