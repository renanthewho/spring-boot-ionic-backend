package com.renanalmeida.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.renanalmeida.domain.Cidade;
import com.renanalmeida.domain.Cliente;
import com.renanalmeida.domain.Endereco;
import com.renanalmeida.domain.enums.TipoCliente;
import com.renanalmeida.dto.ClienteDTO;
import com.renanalmeida.dto.ClienteNewDTO;
import com.renanalmeida.repositories.ClienteRepository;
import com.renanalmeida.repositories.EnderecoRepository;
import com.renanalmeida.services.exceptions.DataIntegrityException;
import com.renanalmeida.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	//Autowired utilizado para instanciar o objeto automaticamente
	// A interface CategoriaRepository é utilizada para, simplesmente, definir o acesso de busca ao banco de dados.
	
	private final String DELETE_CLIENTEEXCEPTION = "Existem pedidos atrelados ao cliente";
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
					"Objeto não encontrado. Id: "+id+" , Tipo: "+ Cliente.class.getName()));
	}
	
	public List<Cliente> findAll(){	
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(int page, int linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage,Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		repo.save(obj);
		enderecoRepository.saveAll(obj.getEndereco());
		return obj;
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}catch(DataIntegrityViolationException e){
			throw new DataIntegrityException(DELETE_CLIENTEEXCEPTION);
		
		}
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj,obj); 
		return repo.save(newObj);
	}
	
	//Usado para insert
	public Cliente fromDTO (ClienteNewDTO objDTO) {
		Cliente cli = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), 
				objDTO.getCpfOuCnpj(), TipoCliente.toEnum(objDTO.getTipoCliente()));
		Cidade cid = new Cidade(objDTO.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), 
				objDTO.getComplemento(), objDTO.getBairro(), objDTO.getCep(), cli, cid);
		cli.getEndereco().add(end);
		cli.getTelefones().add(objDTO.getTelefone1());
		if(objDTO.getTelefone2()!=null) {
			cli.getTelefones().add(objDTO.getTelefone2());
		}
		if(objDTO.getTelefone3()!=null) {
			cli.getTelefones().add(objDTO.getTelefone3());
		}
		return cli;
	}
	//Usado para update
	public Cliente fromDto(ClienteDTO clienteDTO) {
		return new Cliente(null, clienteDTO.getNome(), clienteDTO.getEmail(), 
				null, null);
	}
	
	private void updateData (Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}
