package com.bolsadeideas.springboot.app.models.service;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	@Override
	public void sendEmail() {
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
			messageHelper.setTo("jose.plasencia@getronics.com");
			messageHelper.setFrom("joseplasenc@gmail.com");
			messageHelper.setText("Esto es un correo");
			messageHelper.setSubject("Correo de Prueba");
			//messageHelper.addAttachment("log.txt", new File("C:\\Log\\log.txt"));
			
			this.mailSender.send(mimeMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
