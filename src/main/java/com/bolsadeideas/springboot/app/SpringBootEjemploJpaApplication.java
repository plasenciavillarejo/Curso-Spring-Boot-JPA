package com.bolsadeideas.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;

@EnableScheduling
@SpringBootApplication
/* El parámetro defaultLockAtMostFor especifica la cantidad de tiempo predeterminada que se debe mantener el 
 * bloqueo en caso de que el nodo en ejecución muera. Utiliza el formato de duración ISO8601.*/
@EnableSchedulerLock(defaultLockAtMostFor = "PT30S")
public class SpringBootEjemploJpaApplication  implements CommandLineRunner{

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootEjemploJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		String password = "12345";
		
		for(int i=0; i<2; i ++) {
			String encriptacion = passwordEncoder.encode(password);
			System.out.println("La encriptción para la pas -> 12345 en Bcryps es:" + encriptacion);
		}
		
		
	}

}
