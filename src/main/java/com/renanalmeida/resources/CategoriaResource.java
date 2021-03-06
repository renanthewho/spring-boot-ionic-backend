package com.renanalmeida.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.renanalmeida.domain.Categoria;
import com.renanalmeida.dto.CategoriaDTO;
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
	
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Categoria obj = service.find(id);
		return ResponseEntity.ok(obj);
		
	}
	
	@RequestMapping(method=RequestMethod.POST)
	//Void significa que, no retorno, terá o corpo do Json vazio
	//Request body transforma o corpo do Json em uma classe java.
	//@Valid serve para fazer a validação Bean Validation. somente das categorias, por isso usamos a Categoria DTO
	public ResponseEntity<Void> insert (@Valid @RequestBody CategoriaDTO objDto){
		Categoria obj = service.fromDto(objDto);
		//O método save retorna um objeto, por isso que o atributo recebe o retorno do serviço
		obj = service.insert(obj);
		//Irá montar a URI para a chamada. Isso é feito por padrão para HTTP.
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO objDto, @PathVariable Integer id){
		Categoria obj = service.fromDto(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();	
	}
	
	@RequestMapping(value = "/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	//DTO = Data Transfer Object obtém somente os campos necessários para o negócio, definido pelo cliente.
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		List<Categoria> listCategoria = service.findAll();
		//Método Stream.map serve para percorrer a lista de categorias
		List<CategoriaDTO> listDto = listCategoria.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
		
	}
	
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(value="page", defaultValue = "0")int page, 
			@RequestParam(value = "linesPerPage", defaultValue = "24" )int linesPerPage, 
			@RequestParam(value = "ordeyBy", defaultValue = "nome")String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC")String direction) {
		Page<Categoria> listCategoria = service.findPage(page, linesPerPage, orderBy, direction);
		Page<CategoriaDTO> listDto = listCategoria.map(obj -> new CategoriaDTO(obj));
		return ResponseEntity.ok().body(listDto);
		
	}
}
