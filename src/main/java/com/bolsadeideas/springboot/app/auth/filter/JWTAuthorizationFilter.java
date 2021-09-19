package com.bolsadeideas.springboot.app.auth.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.bolsadeideas.springboot.app.auth.service.JWTService;
import com.bolsadeideas.springboot.app.auth.service.JWTServiceImpl;


/* 1.- Se va a ejecutar en cada REQUEST para evitar esto implementamos un método más abajo -> requiresAuthentication() */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter{
	
	/* Obtenemos el atributo de la Interfaz JWTService*/
	private JWTService jwtService;
	
	/* 1.- Pasamos por el construtor nuestra interfaz JWTService para poder implementar los métodos en la clase. */
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTService jwtService) {
		super(authenticationManager);
		this.jwtService = jwtService;
	}

	/* 1.- Sobreescribimos el método de la clase BasicAuthenticationFilter -> doFilterInternal*/	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
	/* 1.- Creamos la cabecera. */	
		
		String header = request.getHeader(JWTServiceImpl.HEADER_STRING);
		if (!requiresAuthentication(header)) {
			chain.doFilter(request, response);
			return;
		}
		
	/*1.- Definimos el método el cual se encarga de iniciar sesión mediante el UsernamePasswordAuthenticationToken. */
		UsernamePasswordAuthenticationToken authentication = null;

	/* 1.- Pasamos nuestra interfaz jwtService el cual contien el validar token dentro de la cabecera.
	 * 2.- Luego retornamos el usuario y los roles a tráves de la cabecera. */
		
		if(jwtService.validarToken(header)) {
			authentication = new UsernamePasswordAuthenticationToken(jwtService.getUsername(header), null, jwtService.getRoles(header));
		}
		
	/*5.- Llamamos a la clase que se encarga de manejar el contexto de seguridad.
	  		De modo que Autentica al usuario dentro del REQUEST dentro de la petición. */
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}

	/* 2.- Creamos un método que solo se ejecute cuando el autorization exista.*/	
	
	protected boolean requiresAuthentication(String header) {
		
		if(header == null || !header.startsWith(JWTServiceImpl.TOKEN_PREFIX)) {
			return false;
		}
		return true;
	}
}
