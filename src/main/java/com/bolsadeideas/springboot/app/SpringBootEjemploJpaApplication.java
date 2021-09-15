package com.bolsadeideas.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication

/* 1.- Implementamos el CommanLineRunner que nos creará la clase public void run(String... args)*/
public class SpringBootEjemploJpaApplication implements CommandLineRunner {

	/* 1.- Inyectamos el BcrypPasswordEncoder*/
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootEjemploJpaApplication.class, args);
	}

	
	/* 2.- Encriptación de la password de los usuarios alojados en la Base de Datos. .*/
	@Override
	public void run(String... args) throws Exception {
		
		String password="12345";
	/* 3.- Usando passwordEncoder nos ecripta la password.
	 * 4.- Usaremos un for ya que al tener 2 usuarios recorreremos la BD y nos encriptara las 2 password. */
		
		for(int i=0; i <2; i++) {
			String bcryptPassword = passwordEncoder.encode(password);
    /* 5.- Imprimimos el Password por consola. */
			System.out.println("Contraseñas Encriptadas desde SpringBootEjemploJpaApplication.java: (12345) -> " + bcryptPassword);
		}
	}

}
