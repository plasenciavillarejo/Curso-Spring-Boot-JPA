package com.bolsadeideas.springboot.app.auth.service;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.jsonwebtoken.Claims;

public interface JWTService {

	/* 1.- Creamos el metodo crear.
	   2.- Lo creamos a partir de la clase Authentication -> Nos provee del username, roles por este modo usamos el Authentication,  
	   3.- Clase para validar el token. 
	   4.- Clase para obtener los Claims 
	   5.- Clase para obtener el username 
	   6.- Clase para obetner los roles.
	   7.- Clase para resolver el token.
	   */
	
	public String create(Authentication authentication) throws IOException;
	public boolean validarToken(String token);
	public Claims getClaims(String token);
	public String getUsername(String token);
	public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException ;
	public String resolver(String token);
	
}
