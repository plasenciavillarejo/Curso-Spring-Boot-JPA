package com.bolsadeideas.springboot.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvConfig implements WebMvcConfigurer {

	/* 1.- Vamos a crear nuestra pagina de error cuando no se tenga permisos para acceder con el usuario a una vista determinada
	   2.- Vamos a implementar un metodo para registar un controlador de vista "viewController"*/
	
	public void addViewControllers(ViewControllerRegistry registry) {
		/* 1.- Añadimos la ruta del controlador y le indicamos la vista que queremos cargar*/
		registry.addViewController("/error_403").setViewName("error_403");
	}
	
}
