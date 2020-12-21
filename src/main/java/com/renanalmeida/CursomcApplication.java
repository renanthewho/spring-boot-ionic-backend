package com.renanalmeida;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{

	//Autowired para ser instanciado automaticamente
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
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//O id, que é o primeiro campo do construtor Categoria virá nulo
		//pois será preenchido automaticamente com o identity do Banco de Dados.
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		//Instanciando categorias e produtos.
		Produto prod1 = new Produto(null,"Computador", 2000.00);
		Produto prod2 = new Produto(null,"Impressora", 800.00);
		Produto prod3 = new Produto(null,"Impressora", 80.00);

		//Vinculando as categorias aos produtos.
		cat1.getProdutos().addAll(Arrays.asList(prod1,prod2,prod3));
		cat2.getProdutos().addAll(Arrays.asList(prod2));
		//Vinculando os produtos as categorias.
		prod1.getCategorias().addAll(Arrays.asList(cat1));
		prod2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		prod3.getCategorias().addAll(Arrays.asList(cat1));
		
		//Dentro do método correspondente a gravação dos objetos no banco, foi criado
		//um arraylist para inserir os valores dos objetos instanciados.
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(prod1,prod2,prod3));
		
		
		Estado est1 = new Estado(null,"Minas Gerais");
		Estado est2 = new Estado(null,"São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est1);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		
		Cliente cli1 = new Cliente(null,"Maria Silva","maria@gmail.com","2154478520", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("23010208","22154455"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "N/A", "Jardim", "03389010", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Teste", "300", "N/A", "Teste", "03389010", cli1, c2);
		
		cli1.getEndereco().addAll(Arrays.asList(e1,e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1,e2));
		
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