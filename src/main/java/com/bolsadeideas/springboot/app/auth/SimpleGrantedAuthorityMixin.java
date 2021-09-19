package com.bolsadeideas.springboot.app.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/* 1.- Creamos está clase para meterla dentro de la clase JWTAutorizationFilter.java ya que hacer un mix para agregar el constructor
 * cuyo valor será inyectado desde la propiedad @JsonProperty "authority" .*/

public abstract class SimpleGrantedAuthorityMixin {

	@JsonCreator
	public SimpleGrantedAuthorityMixin(@JsonProperty("authority") String role) {

	}

	
}
