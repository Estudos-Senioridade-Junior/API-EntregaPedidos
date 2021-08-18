package com.algaworks.algalog.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.algalog.api.domain.Cliente;

@Repository //Gerencia as entidades
public interface ClienteRepository extends JpaRepository<Cliente, Long>{//Responsável por se comunicar com o banco

	
	List<Cliente> findByNome (String nome);//A depenencia ja usa todos os atributos da classe
	List<Cliente> findByNomeContaining(String nome);
	Optional<Cliente> findByEmail(String email);
		
	
}
