package com.renanalmeida.repositories;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.renanalmeida.domain.Categoria;
import com.renanalmeida.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto,Integer> {
	/*
	 * Find Distinct = Faz o select Distinct pelo campo que queremos procurar, neste caso, nome
	 * Containig utiliza o Like
	 */
	@Transactional(readOnly = true)
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(String nome, List<Categoria>categorias, Pageable pageRequest);

}
