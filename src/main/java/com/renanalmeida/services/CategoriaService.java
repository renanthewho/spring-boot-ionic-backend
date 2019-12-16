package com.renanalmeida.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.renanalmeida.domain.Categoria;
import com.renanalmeida.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	//Autowired utilizado para instanciar o objeto automaticamente
	// A interface CategoriaRepository é utilizada para, simplesmente, definir o acesso de busca ao banco de dados.
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria buscar(Integer id) {
		
		Optional<Categoria> obj = repo.findById(id);
		
		return obj.orElse(null);
		
	}
}
