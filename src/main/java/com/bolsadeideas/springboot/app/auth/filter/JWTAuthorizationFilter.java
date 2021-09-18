package com.bolsadeideas.springboot.app.auth.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


/* 1.- Se va a ejecutar en cada REQUEST para evitar esto implementamos un método más abajo -> requiresAuthentication() */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter{

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	/* 1.- Sobreescribimos el método de la clase BasicAuthenticationFilter -> doFilterInternal*/	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
	/* 1.- Creamos la cabecera. */	
		
		String header = request.getHeader("Authorization");
		if (!requiresAuthentication(header)) {
			chain.doFilter(request, response);
			return;
		}
	
				
		super.doFilterInternal(request, response, chain);
	}

	/* 2.- Creamos un método que solo se ejecute cuando el autorization exista.*/	
	
	protected boolean requiresAuthentication(String header) {
		
		if(header == null || !header.startsWith("Bearer ")) {
			return false;
		}
		return true;
	}
	
	
	
}
