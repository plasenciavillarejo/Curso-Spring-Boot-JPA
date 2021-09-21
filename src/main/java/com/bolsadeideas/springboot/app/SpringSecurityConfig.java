package com.bolsadeideas.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.bolsadeideas.springboot.app.auth.handler.LoginSuccesHandler;
import com.bolsadeideas.springboot.app.models.service.JPAUserDetailService;



@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{

	
	/* Implementamos un método para poder registrar y configurar los usuario de nuestro sistema seguridad para registrar por ejemplo:
	 	Usuario admin, Asignar un username o también asignar roles.*/
	
	
	/* 1.- Empezamos guardando los usuarios en memoria.
	 * 2.- Luego lo guardamos en la BD con JDBC, JPA, etc..  */

	/* Nota: 
	 		Para usar la encriptación dentro de UserBuilder vamos a crear el siguiente método que registra el passwordEncoder como por defecto y lo pasamos al User.passwordEncoder()
	 	@Bean -> Anota la clase como componente Spring */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	/* 1.- Inyectamos la clase service creada.*/
	@Autowired
	private JPAUserDetailService userDetailService;
	
	/* Metodo configureGlobal que recibe por parámetro el AuthenticationManagerBuilder
	   El método va a estar anotado con @Autowired para poder inyectar el método AuthenticationManagerBuilder*/
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
		
	/* 2.- Implementamos SpringSecurity con JPA*/
		
		authenticationManagerBuilder.userDetailsService(userDetailService)
									.passwordEncoder(passwordEncoder());
	}

	/* 1.- Método para Implementar las Autorizaciones (http) de las rutas. Da seguridad a todas nuestras páginas.
	   	  Boton Derecho - Source - Override/Implements Methods -> Sobreescribimos el método configure(HttpSecurity)
	   2.- Inyectamos la clase LoginSuccesHandler que contiene el mensajeFlash cuando un usuario se ha logueado con éxito. */
	
	@Autowired
	private LoginSuccesHandler succesHandler;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		/* 1.- Llamamos al authorizeRequests().antMatchers(String...) las cuales se asignan nuestras rutas.
		   2.- Vamos a incluir las rutas publicas que son de acceso a todo el mundo. antMatchers("/listar","/css/**") por último le permitimos a todos .permitAll()
		   3.- Luego continuamos con las rutas privadas con .hasAnyRole() 
		   4.- Para finalizar invocamos el método anyRequest().authenticated(); 
		   5.- Implementamos nuestro  Login lo permitimos para todos los usuarios -> .formLogin()
		   6.- Vamos a inyectar nuestra página LoginSuccesHandler para que nos indique un mensaje de bienvenida -> .successHandler()
		   			Como vamos a implementar nuestra pagina de login lo indicamos -> .loginPage("/login").permitAll()
		   7.- Implementamos nuestro logout -> .logout().permitAll()
		   8.- Agregamos nuestra página de error. -> .exceptionHandling().accessDeniedPage("error_403")*/
		
		http.authorizeRequests().antMatchers("/","/css/**","/js/**","/images/**","/listar", "/buscarfactura","/usuarioNuevo/**").permitAll()
		.antMatchers("/editar/**").hasAnyRole("ADMIN")
		.antMatchers("/form/**").hasAnyRole("ADMIN")
		.antMatchers("/eliminar/**").hasAnyRole("ADMIN")
		.anyRequest().authenticated()
		.and()
			.formLogin()
			.successHandler(succesHandler)
			.loginPage("/login")
			.permitAll()
		.and()
		.logout().permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/error_403");
	}
	
	
	
}
