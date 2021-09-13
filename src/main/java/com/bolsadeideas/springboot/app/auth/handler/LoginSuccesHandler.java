package com.bolsadeideas.springboot.app.auth.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

/* 1.- En está clase vamos a implementar un mensaje de bienvenida cuando un usuario realiza el login.
   2.- Debemos crear un paquete que contenga la clase LoginSuccesHanderl que extienda SimpleUrlAuthenticationSuccessHandler
   3.- Registramos la clase como @Component -> Se registra como un Bean de Spring para que se pueda inyectar en la clase SpringSecurityConfig.java */


@Component
public class LoginSuccesHandler  extends SimpleUrlAuthenticationSuccessHandler{

	
	/* 1.- Vamos a sobreescribir el método que ya viene predefinido dentro de la clase "Alt +Shit + S" -> Override/Implement Methods*/
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
	
	/* 1.- Obtenemos el Administrador sessiónFlashMapManager para poder registrar un map que contenga los mensajes flash, en este caso el success*/
		SessionFlashMapManager flashManager = new SessionFlashMapManager();
		
	/* 2.- Vamos a crear un Map para los mensajes del tipo -> FlashMap
	   3.- Se está haciendo de está forma por que no se puede inyectar de forma directa como lo estamos haciendo en el contraldor. 
	   RESUMEN: 
	   		  Lo que hacemos es guradar los mensajes flash para mostrarlos en la vista.*/
		FlashMap flashMap = new FlashMap();
		
		flashMap.put("success", "Ha iniciado sesión con éxito!");
		
	/* Pasamos nuestro mensaje al flashManager para guardarlo, con esto ya tendremos implementado nuestra clase. */
		flashManager.saveOutputFlashMap(flashMap, request, response);
		
		super.onAuthenticationSuccess(request, response, authentication);
	}
}
