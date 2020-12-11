package com.renanalmeida.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity  
public class Categoria implements Serializable{	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) //Estratégia para gerar ids automaticamente
	private Integer id;
	private String nome;
	//Json managed reference serve para ele gerenciar a referencia. Usar esta anotação na classe pai
	@JsonManagedReference
	//Criar uma lista de produtos pois uma categoria possui um ou mais produtos.
	//É feito exatamente o mesmo mapeamento que foi feito na classe Produtos que, por sua vez,  foi feito dentro do atributo categorias
	@ManyToMany(mappedBy = "categorias")
	private List<Produto> produtos = new ArrayList<>();
	
	public Categoria() {}

	public Categoria(Integer id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	};
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categoria other = (Categoria) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
