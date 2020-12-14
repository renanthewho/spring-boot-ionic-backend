package com.renanalmeida.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.renanalmeida.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Integer> {

}
