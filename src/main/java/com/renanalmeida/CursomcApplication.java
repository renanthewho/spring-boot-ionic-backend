package com.renanalmeida;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.renanalmeida.domain.Categoria;
import com.renanalmeida.repositories.CategoriaRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{

	//Autowired para ser instanciado automaticamente
	@Autowired
	private CategoriaRepository repo;
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//O id, que é o primeiro campo do construtor Categoria virá nulo
		//pois será preenchido automaticamente com o identity do Banco de Dados.
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		//Dentro do método correspondente a gravação dos objetos no banco, foi criado
		//um arraylist para inserir os valores dos objetos instanciados.
		repo.saveAll(Arrays.asList(cat1, cat2));
		
	}

}