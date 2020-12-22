package com.renanalmeida.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.renanalmeida.domain.Pedido;
import com.renanalmeida.repositories.PedidoRepository;
import com.renanalmeida.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	//Autowired utilizado para instanciar o objeto automaticamente
	// A interface CategoriaRepository é utilizada para, simplesmente, definir o acesso de busca ao banco de dados.
	@Autowired
	private PedidoRepository repo;
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
					"Objeto não encontrado. Id: "+id+" , Tipo: "+ Pedido.class.getName()));
	}
}
