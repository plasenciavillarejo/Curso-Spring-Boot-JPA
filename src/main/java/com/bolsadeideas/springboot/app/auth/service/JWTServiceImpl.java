package com.bolsadeideas.springboot.app.auth.service;

import java.io.IOException;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.bolsadeideas.springboot.app.auth.SimpleGrantedAuthorityMixin;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


@Component
public class JWTServiceImpl implements JWTService{

public static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
public static final long EXPIRATION_DAY = 3600000L;
public static final String TOKEN_PREFIX = "Bearer ";
public static final String HEADER_STRING = "Authorization";

	
	@Override
/* 1.- Authentication authResult -> Contiene ya el usuario authenticado, con todos sus roles. Está con valor true.*/
	public String create(Authentication authResult) throws IOException {
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
		   8.- Fecha de Expiración -> setExpiration() - 3600000 es 1 hora. Se le indica 36000 "L" para indicar que es un long.
		   9- Para finalizar invocamos el método -> compact() */
		
		Collection<? extends GrantedAuthority> roles =  authResult.getAuthorities();		
		Claims claims = Jwts.claims();
		claims.put("authorities", new ObjectMapper().writeValueAsString(roles));
		
		String token = Jwts.builder()
				.setClaims(claims)
				.setSubject(authResult.getName())
				.signWith(SECRET_KEY)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_DAY))
				.compact();	
		return token;
	}
	
	@Override
	public boolean validarToken(String token) {
		
		/* 1.- Llamamos a la clase getClaims para validar el token(la firma) -> .parseClaimsJws(token.replace("Bearer ", "")).*/
		try {	
			getClaims(token);
			return  true;
		}catch (JwtException | IllegalArgumentException e) {
			return  false;
		}
	}

	@Override
	public Claims getClaims(String token) {
		
		/* 1.- Creamos la validación del Token mediante el método Parse para analizar este token a tráves de la clase Jwts
		 * Parser() -> Ya podemos setear o asignar la llave secreta la cual es la que se genera con el token. 
		 * 2.- Se valida nuestro token-> parseClaimsJws():
		 * 		Indicamos un replace, ya que en la cabecera se recibe con "Bearer " y debemos quitar el espacio indicandolo con "" 
		 * 3.- Claims para devolver el token con todos sus datos.*/	
		
		Claims claims = Jwts.parser()
		.setSigningKey(SECRET_KEY)
		.parseClaimsJws(resolver(token))
		.getBody();
		
		return claims;
	}

	@Override
	public String getUsername(String token) {
		/* 1.- Reutilizamos el getClains para obtener el Username. -> .getSubject() nos da el username */				
		return getClaims(token).getSubject();
	}

	
	@Override
	public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException {
		Object roles = getClaims(token).get("authorities");
		
		/* 1.-  Los roles viene en tipo JSON y tenemos que convertilos en GrandesAutorities de una collection.
		 * 2.- Agregamos la clase SimplegrantedAuthoritiesMixin.java ya que si no, nos dara error y lo debemos meter dentro de un Array.asList()*/
		Collection<? extends GrantedAuthority> authorities = Arrays.asList(new ObjectMapper()
				.addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
				.readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));
		return authorities;
	}

	@Override
	public String resolver(String token) {
		/* 1.- Solo falta integrar lo siguiente token.replace("Bearer ", "")*/
		
		if (token != null && token.startsWith(TOKEN_PREFIX)) {
			return token.replace(TOKEN_PREFIX, "");
		}else {
			return null;
		}
	}

}
