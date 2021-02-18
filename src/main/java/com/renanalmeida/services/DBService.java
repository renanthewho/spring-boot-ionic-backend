package com.renanalmeida.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.renanalmeida.domain.Categoria;
import com.renanalmeida.domain.Cidade;
import com.renanalmeida.domain.Cliente;
import com.renanalmeida.domain.Endereco;
import com.renanalmeida.domain.Estado;
import com.renanalmeida.domain.ItemPedido;
import com.renanalmeida.domain.Pagamento;
import com.renanalmeida.domain.PagamentoComBoleto;
import com.renanalmeida.domain.PagamentoComCartão;
import com.renanalmeida.domain.Pedido;
import com.renanalmeida.domain.Produto;
import com.renanalmeida.domain.enums.EstadoPagamento;
import com.renanalmeida.domain.enums.Perfil;
import com.renanalmeida.domain.enums.TipoCliente;
import com.renanalmeida.repositories.CategoriaRepository;
import com.renanalmeida.repositories.CidadeRepository;
import com.renanalmeida.repositories.ClienteRepository;
import com.renanalmeida.repositories.EnderecoRepository;
import com.renanalmeida.repositories.EstadoRepository;
import com.renanalmeida.repositories.ItemPedidoRepository;
import com.renanalmeida.repositories.PagamentoRepository;
import com.renanalmeida.repositories.PedidoRepository;
import com.renanalmeida.repositories.ProdutoRepository;

@Service
public class DBService {
	
	//Autowired para ser instanciado automaticamente
		@Autowired
		private BCryptPasswordEncoder pe;
		@Autowired
		private CategoriaRepository categoriaRepository;
		@Autowired
		private ProdutoRepository produtoRepository;
		@Autowired
		private CidadeRepository cidadeRepository;
		@Autowired
		private EstadoRepository estadoRepository;
		@Autowired
		private ClienteRepository clienteRepository;
		@Autowired
		private EnderecoRepository enderecoRepository;
		@Autowired
		private PagamentoRepository pagamentoRepository;
		@Autowired
		private PedidoRepository pedidoRepository;
		@Autowired
		private ItemPedidoRepository itemRepository;
	
	public void instantiateTestDatabase() throws ParseException {
		
		//O id, que é o primeiro campo do construtor Categoria virá nulo
				//pois será preenchido automaticamente com o identity do Banco de Dados.
				Categoria cat1 = new Categoria(null, "Informática");
				Categoria cat2 = new Categoria(null, "Escritório");
				Categoria cat3 = new Categoria(null, "Cama Mesa e Banho");
				Categoria cat4 = new Categoria(null, "Eletrônicos");
				Categoria cat5 = new Categoria(null, "Jardinagem");
				Categoria cat6 = new Categoria(null, "Decoração");
				Categoria cat7 = new Categoria(null, "Perfumaria");
				
				
				//Instanciando categorias e produtos.
				Produto prod1  = new Produto(null,"Computador", 2000.00);
				Produto prod2  = new Produto(null,"Impressora", 800.00);
				Produto prod3  = new Produto(null,"Mouse", 80.00);
				Produto prod4  = new Produto(null,"Mesa de Escritório", 300.00);
				Produto prod5  = new Produto(null,"Toalha", 50.00);
				Produto prod6  = new Produto(null,"Colcha", 200.00);
				Produto prod7  = new Produto(null,"TV true color", 1200.00);
				Produto prod8  = new Produto(null,"Roçadeira", 800.00);
				Produto prod9  = new Produto(null,"Abajour", 100.00);
				Produto prod10 = new Produto(null,"Pendente", 180.00);
				Produto prod11 = new Produto(null,"Shampoo", 90.00);

				//Vinculando as categorias aos produtos.
				cat1.getProdutos().addAll(Arrays.asList(prod1,prod2,prod3));
				cat2.getProdutos().addAll(Arrays.asList(prod2,prod4));
				cat3.getProdutos().addAll(Arrays.asList(prod5,prod6));
				cat4.getProdutos().addAll(Arrays.asList(prod1,prod2,prod3,prod7));
				cat5.getProdutos().addAll(Arrays.asList(prod8));
				cat6.getProdutos().addAll(Arrays.asList(prod9,prod10));
				cat7.getProdutos().addAll(Arrays.asList(prod11));
				
				//Vinculando os produtos as categorias.
				prod1.getCategorias().addAll(Arrays.asList(cat1,cat4));
				prod2.getCategorias().addAll(Arrays.asList(cat1,cat2,cat4));
				prod3.getCategorias().addAll(Arrays.asList(cat1,cat4));
				prod4.getCategorias().addAll(Arrays.asList(cat2));
				prod5.getCategorias().addAll(Arrays.asList(cat3));
				prod6.getCategorias().addAll(Arrays.asList(cat3));
				prod7.getCategorias().addAll(Arrays.asList(cat4));
				prod8.getCategorias().addAll(Arrays.asList(cat5));
				prod9.getCategorias().addAll(Arrays.asList(cat6));
				prod10.getCategorias().addAll(Arrays.asList(cat6));
				prod11.getCategorias().addAll(Arrays.asList(cat7));
				
				//Dentro do método correspondente a gravação dos objetos no banco, foi criado
				//um arraylist para inserir os valores dos objetos instanciados.
				categoriaRepository.saveAll(Arrays.asList(cat1, cat2,cat3,cat4,cat5,cat6,cat7));
				produtoRepository.saveAll(Arrays.asList(prod1,prod2,prod3,
						prod4,prod5,prod6,prod7,prod8,prod9,prod10,prod11));
				
				
				Estado est1 = new Estado(null,"Minas Gerais");
				Estado est2 = new Estado(null,"São Paulo");
				
				Cidade c1 = new Cidade(null, "Uberlândia", est1);
				Cidade c2 = new Cidade(null, "São Paulo", est2);
				Cidade c3 = new Cidade(null, "Campinas", est1);
				
				est1.getCidades().addAll(Arrays.asList(c1));
				est2.getCidades().addAll(Arrays.asList(c2,c3));
				
				estadoRepository.saveAll(Arrays.asList(est1, est2));
				cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
				
				Cliente cli1 = new Cliente(null,"Maria Silva","renanalmeida1991@yahoo.com.br","2154478520", TipoCliente.PESSOAFISICA, pe.encode("123"));
				cli1.getTelefones().addAll(Arrays.asList("23010208","22154455"));
				
				Cliente cli2 = new Cliente(null,"Ana Costa","realmeida100@gmail.com","29925556066", TipoCliente.PESSOAFISICA, pe.encode("123"));
				cli2.getTelefones().addAll(Arrays.asList("23010550","22145577"));
				cli2.addPerfil(Perfil.ADMIN);
				
				Endereco e1 = new Endereco(null, "Rua Flores", "300", "N/A", "Jardim", "03389010", cli1, c1);
				Endereco e2 = new Endereco(null, "Avenida Teste", "300", "N/A", "Teste", "03389010", cli1, c2);
				Endereco e3 = new Endereco(null, "Avenida xpto", "450", null, "Teste", "03389012", cli2, c2);
				
				cli1.getEndereco().addAll(Arrays.asList(e1,e2));
				cli2.getEndereco().addAll(Arrays.asList(e3));
				
				clienteRepository.saveAll(Arrays.asList(cli1,cli2));
				enderecoRepository.saveAll(Arrays.asList(e1,e2,e3));
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				Pedido ped1 = new Pedido(null, sdf.parse("14/12/2020 10:32"), cli1, e1);
				Pedido ped2 = new Pedido(null, sdf.parse("13/12/2020 16:41"), cli1, e2);
				
				Pagamento pagto1 = new PagamentoComCartão(null, EstadoPagamento.QUITADO, ped1, 6);
				ped1.setPagamento(pagto1);
				Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("13/12/2020 12:35"), null);
				ped2.setPagamento(pagto2);
				
				cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
				
				pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
				pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
				
				ItemPedido ip1 = new ItemPedido(prod1, ped1, 0.00, 1, 2000.00);
				ItemPedido ip2 = new ItemPedido(prod3, ped1, 0.00, 2, 80.00);
				ItemPedido ip3 = new ItemPedido(prod2, ped2, 100.00, 1, 800.00);
				
				ped1.getItens().addAll(Arrays.asList(ip1,ip2));
				ped2.getItens().addAll(Arrays.asList(ip3));
				
				prod1.getItens().addAll(Arrays.asList(ip1));
				prod2.getItens().addAll(Arrays.asList(ip3));
				prod3.getItens().addAll(Arrays.asList(ip2));
				
				itemRepository.saveAll(Arrays.asList(ip1,ip2,ip3));
	}

}
