package com.bolsadeideas.springboot.app.auth.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.bolsadeideas.springboot.app.auth.SimpleGrantedAuthoritiesMixin;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;


/* 1.- Se va a ejecutar en cada REQUEST para evitar esto implementamos un método más abajo -> requiresAuthentication() */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter{

	@Autowired
	private JWTAuthenticationFilter authenticationFilter;
	
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

	/* 2.- Creamos la validación del Token mediante el método Parse para analizar este token a tráves de la clase Jwts
	 * Parser() -> Ya podemos setear o asignar la llave secreta la cual es la que se genera con el token. 
	 * 3.- Se valida nuestro token que lo obtenemos desde la cabecera (Header)
	 * 		Indicamos un replace, ya que en la cabecera se recibe con "Bearer " y debemos quitar el espacio indicandolo con "" 
	 * 4.- Creamos un boolean para validar todos los casos restantes. 
	 * 5.- Claims para devolver el token con todos sus datos.*/	
	
		boolean validoToken;
		Claims token = null;
		try {	
			
			token = Jwts.parser()
			.setSigningKey(authenticationFilter.SECRET_KEY)
			.parseClaimsJws(header.replace("Bearer ", "")).getBody();
			validoToken = true;
		}catch (JwtException | IllegalArgumentException e) {
			validoToken = false;
		}
		
		
		/*1.- Definimos el método el cual se encarga de iniciar sesión mediante el UsernamePasswordAuthenticationToken. */
		UsernamePasswordAuthenticationToken authentication = null;
		
		
		/* 1.- Realizamos la autenticación
		 * 2.- Obtenemos el user y los roles.
		 * 3.- Nombre del usuario se obtiene mediante getSubjetc()
		 * 4.- Los roles lo obtenemos de la clase JWTAuthenticationFilter -> claims.put("authorities", new ObjectMapper().writeValueAsString(roles));*/
		if(validoToken) {
			String username= token.getSubject();
			Object roles = token.get("authorities");	
			
			/* 1.- Tenemos que crear los roles de una collection
			 * 2.- Agregamos la clase SimplegrantedAuthoritiesMixin.java */
			
			Collection<? extends GrantedAuthority> authorities = Arrays.asList(new ObjectMapper()
					.addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthoritiesMixin.class)
					.readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));
			
			authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
		}
		
		/*5.- Llamamos a la clase que se encarga de manejar el contexto de seguridad.
		 * De modo que Autentica al usuario dentro del REQUEST dentro de la petición. */
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}

	/* 2.- Creamos un método que solo se ejecute cuando el autorization exista.*/	
	
	protected boolean requiresAuthentication(String header) {
		
		if(header == null || !header.startsWith("Bearer ")) {
			return false;
		}
		return true;
	}
	
	
	
}
