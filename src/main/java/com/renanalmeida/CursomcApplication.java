package com.renanalmeida;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.renanalmeida.domain.Categoria;
import com.renanalmeida.domain.Produto;
import com.renanalmeida.repositories.CategoriaRepository;
import com.renanalmeida.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{

	//Autowired para ser instanciado automaticamente
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//O id, que é o primeiro campo do construtor Categoria virá nulo
		//pois será preenchido automaticamente com o identity do Banco de Dados.
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		//Instanciando categorias e produtos.
		Produto prod1 = new Produto(null,"Computador", 2000.00);
		Produto prod2 = new Produto(null,"Impressora", 800.00);
		Produto prod3 = new Produto(null,"Impressora", 80.00);
		
		//Vinculando as categorias aos produtos.
		cat1.getProdutos().addAll(Arrays.asList(prod1,prod2,prod3));
		cat2.getProdutos().addAll(Arrays.asList(prod2));
		//Vinculando os produtos as categorias.
		prod1.getCategorias().addAll(Arrays.asList(cat1));
		prod2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		prod3.getCategorias().addAll(Arrays.asList(cat1));
		
		//Dentro do método correspondente a gravação dos objetos no banco, foi criado
		//um arraylist para inserir os valores dos objetos instanciados.
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(prod1,prod2,prod3));
		
	}

}