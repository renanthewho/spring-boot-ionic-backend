package com.renanalmeida.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.renanalmeida.domain.Pedido;
import com.renanalmeida.services.PedidoService;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoResource {
	
	//Esta camada acessa o serviço que, por sua vez, acessa a camada de acesso a dados (repository)
	@Autowired
	private PedidoService service;
	
	@RequestMapping(value = "/{id}", method=RequestMethod.GET) //Significa que a funçâo abaixo fará um GET de informaçâo.
	//Tipo response entity armazena informacoes como response http
	//Anotaçâo PathVariable serve para obter o valor do ID informado através do servico, ou seja, o id informado no 
	//Request Mapping
	
	public ResponseEntity<?> listar(@PathVariable Integer id) {
		Pedido obj = service.find(id);
		return ResponseEntity.ok(obj);
		
	}
	
	@RequestMapping(method=RequestMethod.POST)
	//Request body transforma o corpo do Json em uma classe java.
	//@Valid serve para fazer a validação Bean Validation. somente das categorias, por isso usamos a Categoria DTO
	public ResponseEntity<Void> insert (@Valid @RequestBody Pedido obj){
		//O método save retorna um objeto, por isso que o atributo recebe o retorno do serviço
		obj = service.insert(obj);
		//Irá montar a URI para a chamada. Isso é feito por padrão para HTTP.
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

}
