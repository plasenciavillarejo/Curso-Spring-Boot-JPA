package com.bolsadeideas.springboot.app.auth.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bolsadeideas.springboot.app.auth.service.JWTService;
import com.bolsadeideas.springboot.app.auth.service.JWTServiceImpl;
import com.bolsadeideas.springboot.app.models.entity.Usuario;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/* 1.- Filtro encargado de realizar el login.
 * 2.- Hereda de una clase UsernamePasswordAuthenticationFilter para customizar nuestro login.  */

public class JWTAuthenticationFilter extends  UsernamePasswordAuthenticationFilter{
	
	/* 1.- Encargado de hacer el login*/
	private AuthenticationManager authenticationManager;
		
	/* 1.- Inyectamos la Interfaz JWTService y lo pasamos por el constructor de está forma podremos usarlo dentro de la clase*/
	private JWTService jwtService;
		
	/* Creamos el Constructor Generate Constructor using Fileds*/
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTService jwtService) {
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
	}

	/* 1.- Vamos a sobreescribir el metodo que ya está implementado en la clase. attemptAuthentication se encarga de autenticar */
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
	/* 1.- Copiamos el método ya implementado dentro de la clase UsernamePasswordAuthenticationFilter. */	
		
		String username = obtainUsername(request);
		String password = obtainPassword(request);
			
		if(username != null && password != null) {
			logger.info("Username desde resquest parameter (form-data): " + username);
			logger.info("Password desde resquest parameter (form-data): " + password);
		}
	/* 2.- Aquí vamos a implementar cuando enviamos el usuario y la password mediante un formato Postman -> Body / Raw 
	 * 3.- Convertimos los datos que estamos recibiendo a un objeto usuario. -> request.getInputStream(). 
	 * 4.- Asignamos el Username -> username = user.getUsername();
	 * 5.- Asignamos el Passwrod -> password = user.getPassword();*/
		else {
			Usuario user = null;
			try {
				user = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
				username = user.getUsername();
				password = user.getPassword();
				logger.info("Username desde resquest InputStream (raw): " + username);
				logger.info("Password desde resquest InputStream (raw): " + password);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		username = username.trim();
		
		/* 1.- Si todo ha ido bien se genera un token a nivel de servidor mediante el usuario y el password. 
		   Nota: Este token que se genera es diferentes al toke que genera JWTokens, no confundir con ello. */
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
		
		setDetails(request, authRequest);
		
	/* 2.- Creamos un contenedor que se encarga de contener las credenciales. 
	 * 3.- Recibe como argumento el username y el password.
	 * 4.- Retornamos el autenticación inyectada arriba -> authenticationManager.authenticate()  */
		
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,password);
		return authenticationManager.authenticate(authToken);
	}

	/* 1.- Vamos a crear el JWSToken -> Override/implements -> AbstractAuthenticationProcessingFilter -> successfulAuthentication() */
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		/* 1.- Pasamos el Token creado en la clase JWTServiceImple.java -> create(authResult)*/
		String token = jwtService.create(authResult);
				
		/* 1.- Pasamos el Token en la cabecera de la respuesta para el usuario. 
		 * 2.- Se debe seguir el patron siempre:
		 		"Authorization"  "Bearer"  */
		response.addHeader(JWTServiceImpl.HEADER_STRING, JWTServiceImpl.TOKEN_PREFIX + token);
		
		/* 1.- Es recomendable pasarlo al usuario el token en una estructura JSON. */
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("token", token);
		body.put("user",(User) authResult.getPrincipal());
		body.put("mensaje", "Has iniciado la sesión con éxito.!!");
		
		/* 2.- Pasamos los datos de arriba a la respuesta. 
		 * 3.- new ObjecMapper() -> Nos permite pasar cualquier objeto de java a un JSON. 
		 * 4.- Indicamos el status -> getStatus. 
		 * 5.- setContentType -> Indicamos el retorno que deseamos*/		
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setStatus(200);
		response.setContentType("application/json");
	}
	
	/* 1.- Implementacíon del Método cuando se realiza el login con credenciales distintas.*/
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("mensaje", "Error de autenticación: username o password incorrecto!");
		body.put("error", failed.getMessage());
		
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setStatus(401);
		response.setContentType("application/json");
	}
}
