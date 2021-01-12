package com.renanalmeida.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.renanalmeida.domain.ItemPedido;
import com.renanalmeida.domain.PagamentoComBoleto;
import com.renanalmeida.domain.Pedido;
import com.renanalmeida.domain.enums.EstadoPagamento;
import com.renanalmeida.repositories.ItemPedidoRepository;
import com.renanalmeida.repositories.PagamentoRepository;
import com.renanalmeida.repositories.PedidoRepository;
import com.renanalmeida.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	//Autowired utilizado para instanciar o objeto automaticamente
	// A interface CategoriaRepository é utilizada para, simplesmente, definir o acesso de busca ao banco de dados.
	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
					"Objeto não encontrado. Id: "+id+" , Tipo: "+ Pedido.class.getName()));
	}
	
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setDataPedido(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getDataPedido());
		}
		repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for(ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setPreco(produtoService.find(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		return obj;
	}
}
