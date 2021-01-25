package com.renanalmeida.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.renanalmeida.services.DBService;
import com.renanalmeida.services.EmailService;
import com.renanalmeida.services.MockEmailService;
//Classe para fazer a instanciação do banco de dados com os valores dos atributos preenchidos.
@Configuration
@Profile("test")
public class TestConfig {
	
	@Autowired
	private DBService dbService;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		dbService.instantiateTestDatabase();
		return true;
	}
	
	/*
	 * Classe de configuração. Para que, quando instanciar a interface EmailService, instancie a classe MockEmailService.
	 */
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}

}
