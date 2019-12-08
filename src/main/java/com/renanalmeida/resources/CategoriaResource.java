package com.renanalmeida.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@RequestMapping(method=RequestMethod.GET) //Significa que a funçâo abaixo fará um GET de informaçâo.
	public String listar() {
		
		return "REST está funcionando!!";
		
	}

}
