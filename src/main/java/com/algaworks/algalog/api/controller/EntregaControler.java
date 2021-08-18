package com.algaworks.algalog.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algalog.api.assembler.EntregaAssembler;
import com.algaworks.algalog.api.domain.Entrega;
import com.algaworks.algalog.api.domain.EntregaModel;
import com.algaworks.algalog.api.domain.input.EntregaInput;
import com.algaworks.algalog.api.repository.EntregaRepository;
import com.algaworks.algalog.api.service.FinalizacaoEntregaService;
import com.algaworks.algalog.api.service.SolicitacaoEntregaService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/entregas")
public class EntregaControler {

	private SolicitacaoEntregaService solicitacaoEntregaService;
	private EntregaRepository entregaRepository;
	private EntregaAssembler entregaAssembler;
	private FinalizacaoEntregaService finalizacaoEntregaService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)//se for criado responde o código de criado
	public EntregaModel solicitar(@Valid @RequestBody EntregaInput entregaInput) {
		Entrega novaEntrega = entregaAssembler.toEntity(entregaInput);
		Entrega entregaSolicitada = solicitacaoEntregaService.Solicitar(novaEntrega);
			
		return entregaAssembler.toModel(entregaSolicitada);
		
	}
	
	@GetMapping
	public List<EntregaModel> listar(){
		return entregaAssembler.toCollectionModel(entregaRepository.findAll());
	}
	
	@GetMapping("/{entregaId}")
	public ResponseEntity<EntregaModel> buscar (@PathVariable Long entregaId){
		return entregaRepository.findById(entregaId)
				.map(entrega -> {//entrega recebe um object do resultado do findbyid
					EntregaModel entregaModel = entregaAssembler.toModel(entrega);
					
				return ResponseEntity.ok(entregaModel);
				}).orElse(ResponseEntity.notFound().build());
	}
	
	@PutMapping ("/{entregaId}/finalizacao")
	@ResponseStatus (HttpStatus.NO_CONTENT)
	public void finalizar (@PathVariable Long entregaId) {
		finalizacaoEntregaService.finalizar(entregaId);
	}
}
