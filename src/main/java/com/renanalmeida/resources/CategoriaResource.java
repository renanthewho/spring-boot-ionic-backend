package com.renanalmeida.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
		Categoria obj = service.find(id);
		return ResponseEntity.ok(obj);
		
	}
	
	@RequestMapping(method=RequestMethod.POST)
	//Void significa que, no retorno, terá o corpo do Json vazio
	//Request body transforma o corpo do Json em uma classe java.
	public ResponseEntity<Void> insert (@RequestBody Categoria obj){
		//O método save retorna um objeto, por isso que o atributo recebe o retorno do serviço
		obj = service.insert(obj);
		//Irá montar a URI para a chamada. Isso é feito por padrão para HTTP.
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Categoria obj, @PathVariable Integer id){
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();	
	}
	
	@RequestMapping(value = "/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
