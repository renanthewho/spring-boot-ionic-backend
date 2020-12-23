package com.renanalmeida.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.renanalmeida.domain.Categoria;
import com.renanalmeida.repositories.CategoriaRepository;
import com.renanalmeida.services.exceptions.DataIntegrityException;
import com.renanalmeida.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	final String DELETE_CATEGORIAEXCEPTION = "Não é possível excluir categorias com produtos atrelados";

	//Autowired utilizado para instanciar o objeto automaticamente
	// A interface CategoriaRepository é utilizada para, simplesmente, definir o acesso de busca ao banco de dados.
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
					"Objeto não encontrado. Id: "+id+" , Tipo: "+ Categoria.class.getName()));
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		find(obj.getId());
		return repo.save(obj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}catch(DataIntegrityViolationException e){
			throw new DataIntegrityException(DELETE_CATEGORIAEXCEPTION);
		
		}
	}
	
	public List<Categoria> findAll(){	
		return repo.findAll();
	}
}
