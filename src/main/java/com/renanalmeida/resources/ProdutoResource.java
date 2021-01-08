package com.renanalmeida.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.renanalmeida.domain.Produto;
import com.renanalmeida.dto.ProdutoDTO;
import com.renanalmeida.resources.utils.URL;
import com.renanalmeida.services.ProdutoService;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {
	
	//Esta camada acessa o serviço que, por sua vez, acessa a camada de acesso a dados (repository)
	@Autowired
	private ProdutoService service;
	
	@RequestMapping(value = "/{id}", method=RequestMethod.GET) //Significa que a funçâo abaixo fará um GET de informaçâo.
	//Tipo response entity armazena informacoes como response http
	//Anotaçâo PathVariable serve para obter o valor do ID informado através do servico, ou seja, o id informado no 
	//Request Mapping
	
	public ResponseEntity<?> listar(@PathVariable Integer id) {
		Produto obj = service.find(id);
		return ResponseEntity.ok(obj);
		
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value="nome", defaultValue = "0")String nome,
			@RequestParam(value="categorias", defaultValue = "0")String categorias,
			@RequestParam(value="page", defaultValue = "0")int page, 
			@RequestParam(value = "linesPerPage", defaultValue = "24" )int linesPerPage, 
			@RequestParam(value = "ordeyBy", defaultValue = "nome")String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC")String direction) {
		String nomeDecoded = URL.decodeParam(nome);
		List<Integer>ids = URL.decodeIntList(categorias);
		Page<Produto> list = service.search(nomeDecoded,ids,page, linesPerPage, orderBy, direction);
		Page<ProdutoDTO> listDto = list.map(obj -> new ProdutoDTO(obj));
		return ResponseEntity.ok().body(listDto);
		
	}
}
