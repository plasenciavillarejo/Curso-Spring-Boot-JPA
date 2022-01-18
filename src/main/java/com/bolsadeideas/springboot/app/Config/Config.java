package com.bolsadeideas.springboot.app.Config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class Config {

	@Bean
	public JavaMailSenderImpl javaMailSenderImpl() {
		JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
		mailSenderImpl.createMimeMessage();
		mailSenderImpl.setHost("smtp.gmail.com");
		mailSenderImpl.setPort(587);
		mailSenderImpl.setUsername("plasenciavillarejo@gmail.com");
		mailSenderImpl.setPassword("excobiwlzscnrxol");
		
		//

		Properties props = mailSenderImpl.getJavaMailProperties();

		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.starttls.required", "true");
		props.put("mail.smtp.connectiontimeout", "5000");
		props.put("mail.smtp.timeout", "3000");
		props.put("mail.smtp.writetimeout", "5000");

		return mailSenderImpl;
	}

}