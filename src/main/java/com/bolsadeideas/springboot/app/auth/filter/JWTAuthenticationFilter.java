package com.bolsadeideas.springboot.app.auth.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/* 1.- Filtro encargado de realizar el login.
 * 2.- Hereda de una clase UsernamePasswordAuthenticationFilter para customizar nuestro login.  */

public class JWTAuthenticationFilter extends  UsernamePasswordAuthenticationFilter{

	
	/* 1.- Encargado de hacer el login*/
	private AuthenticationManager authenticationManager;
		
	/* Creamos el Constructor Generate Constructor using Fileds*/
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	/* 1.- Vamos a sobreescribir el metodo que ya está implementado en la clase. attemptAuthentication se encarga de autenticar */
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
	/* 1.- Copiamos el método ya implementado dentro de la clase UsernamePasswordAuthenticationFilter. */	
		
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		
		username = (username != null) ? username : "";
		username = username.trim();
		
		password = (password != null) ? password : "";
		
		if(username != null && password != null) {
			logger.info("Username desde resquest parameter (form-data): " + username);
			logger.info("Password desde resquest parameter (form-data): " + password);
		}
		
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);
		
	/* 2.- Creamos un contenedor que se encarga de contener las credenciales. 
	 * 3.- Recibe como argumento el username y el password.
	 * 4.- Retornamos el autenticación inyectada arriba -> authenticationManager.authenticate()  */
		
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,password);
		return authenticationManager.authenticate(authToken);
	}


	
	
}
