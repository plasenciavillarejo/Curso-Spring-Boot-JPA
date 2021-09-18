package com.bolsadeideas.springboot.app.auth.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


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
		password = (password != null) ? password : "";
		
		if(username != null && password != null) {
			logger.info("Username desde resquest parameter (form-data): " + username);
			logger.info("Password desde resquest parameter (form-data): " + password);
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
	
	/* 1.- Authentication authResult -> Contiene ya el usuario authenticado, con todos sus roles. Está con valor true.*/
			Authentication authResult) throws IOException, ServletException {
		
		/* 1.- Creación del Token JWT.
		   2.- Asignamos los datos del token -> Nombre del usuario , setSubject(authResult.getName(), 
		   3.- Otra forma de obtener el usuario des de la siguiente forma:
		   		String username = ((User) authResult.getPrincipal()).getUsername()) 
		   4.- Firmamos nuestro token -> 
		   		Debemos crear nuestra Llave secreta de forma autómatica ->  SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
		   5.- Fecha de Creacíon Token -> setIssuedAt(Date iat)
		   6.- Obtenemos los roles -> Collection<? extends GrantedAuthority> roles =  authResult.getAuthorities()
		   		Lo tenemos que pasar mediante un Claims ya que es un dato extra que quermos incluir.
		   7.- Obtenemos los Claims. -> Claims claims = Jwts.claims();
		   		Tenemos que pasar los roles a un objeto json. -> new ObjectMapper().writeValueAsString(roles)
		   7.- Fecha de Expiración -> setExpiration() - 3600000 es 1 hora. Se le indica 36000 "L" para indicar que es un long.
		   
		   
		   .- Para finalizar invocamos el método -> compact() */
		
		SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
		
		Collection<? extends GrantedAuthority> roles =  authResult.getAuthorities();		
		Claims claims = Jwts.claims();
		claims.put("authorities", new ObjectMapper().writeValueAsString(roles));
		
		String token = Jwts.builder()
				.setClaims(claims)
				.setSubject(authResult.getName())
				.signWith(secretKey)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 3600000L))
				.compact();
				
		/* 1.- Pasamos el Token en la cabecera de la respuesta para el usuario. 
		 * 2.- Se debe seguir el patron siempre:
		 		"Authorization"  "Bearer"  */
		
		response.addHeader("Authorization", "Bearer " + token);
		
		
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
	
	
	
	
	
	
	
	
	
	
	
	
}
