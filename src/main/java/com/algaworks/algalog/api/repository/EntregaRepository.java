package com.algaworks.algalog.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.algalog.api.domain.Entrega;

@Repository
public interface EntregaRepository extends JpaRepository<Entrega, Long>{

}

