package com.renanalmeida.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.renanalmeida.domain.Categoria;
import com.renanalmeida.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	//Esta camada acessa o serviço que, por sua vez, acessa a camada de acesso a dados (repository)
	@Autowired
	private CategoriaService service;
	
	@RequestMapping(value = "/{id}", method=RequestMethod.GET) //Significa que a funçâo abaixo fará um GET de informaçâo.
	//Tipo response entity armazena informacoes como response http
	//Anotaçâo PathVariable serve para obter o valor do ID informado através do servico, ou seja, o id informado no 
	//Request Mapping
	
	public ResponseEntity<?> listar(@PathVariable Integer id) {
		Categoria obj = service.buscar(id);
		return ResponseEntity.ok(obj);
		
	}

}
