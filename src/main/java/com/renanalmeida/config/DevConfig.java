package com.renanalmeida.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.renanalmeida.services.DBService;
//Classe para fazer a instanciação do banco de dados com os valores dos atributos preenchidos.
@Configuration
@Profile("dev")
public class DevConfig {
	
	@Autowired
	private DBService dbService;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	private final String CREATE = "create";
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		
		if(!strategy.equals(CREATE)) {
			return false;
		}
		dbService.instantiateTestDatabase();
		return true;
	}

}
