package com.renanalmeida.domain;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.renanalmeida.domain.enums.EstadoPagamento;

@Entity
/*
 * Conforme o JsonTypeFormat da classe Pagamento, o TypeName serve para indicar como a descricao
 * vem disposta no JSON.
 */
@JsonTypeName("pagamentoComCartao")
public class PagamentoComCartão extends Pagamento{
	private static final long serialVersionUID = 1L;

	private Integer numeroDeParcelas;

	public PagamentoComCartão(Integer id, EstadoPagamento estado, Pedido pedido, Integer numeroDeParcelas) {
		super(id, estado, pedido);
		this.numeroDeParcelas = numeroDeParcelas;
	}
	
	public PagamentoComCartão() {
	}

	public Integer getNumeroDeParcelas() {
		return numeroDeParcelas;
	}

	public void setNumeroDeParcelas(Integer numeroDeParcelas) {
		this.numeroDeParcelas = numeroDeParcelas;
	}
	
}
