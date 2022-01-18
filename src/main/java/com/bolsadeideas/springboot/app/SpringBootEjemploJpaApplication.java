package com.bolsadeideas.springboot.app;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringBootEjemploJpaApplication  implements CommandLineRunner{


	private final Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
	
	protected final Log log2 = LogFactory.getLog(this.getClass());
	
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
			log.debug("La contraseña es " + encriptacion);
		}
		 
		 log2.info("Claro que si primo " + password);
		
	}
	
}
