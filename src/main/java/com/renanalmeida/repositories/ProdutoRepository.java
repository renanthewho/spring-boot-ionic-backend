package com.renanalmeida.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.renanalmeida.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto,Integer> {

}
