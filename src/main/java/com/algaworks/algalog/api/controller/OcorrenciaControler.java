package com.algaworks.algalog.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algalog.api.assembler.OcorrenciaAssembler;
import com.algaworks.algalog.api.domain.Entrega;
import com.algaworks.algalog.api.domain.Ocorrencia;
import com.algaworks.algalog.api.domain.OcorrenciaModel;
import com.algaworks.algalog.api.domain.input.OcorrenciaInput;
import com.algaworks.algalog.api.service.BuscaEntregaService;
import com.algaworks.algalog.api.service.RegistroOcorrenciaService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/entregas/{entregaId}/ocorrencias")

public class OcorrenciaControler {
	
	private RegistroOcorrenciaService registroOcorrenciaService; 
	private OcorrenciaAssembler ocorrenciaAssembler;
	private BuscaEntregaService buscaEntregaService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OcorrenciaModel registrar (@PathVariable Long entregaId, 
			@Valid @RequestBody OcorrenciaInput ocorrenciaInput) {
		Ocorrencia ocorrenciaRegistrada = registroOcorrenciaService
				.registrar(entregaId, ocorrenciaInput.getDescricao());
				
				return ocorrenciaAssembler.toModel(ocorrenciaRegistrada);
				//Este método não possui o SaveAll(para persistir no banco de dados
				//pq ele faz através do cascade que consta na classe entrega.
		
	}
	
	@GetMapping
	public List<OcorrenciaModel> listar (@PathVariable Long entregaId){
		Entrega entrega = buscaEntregaService.buscar(entregaId);
		return ocorrenciaAssembler.toCollectionModel(entrega.getOcorrencias());
		//O get ocorrencias como esstá ligado por atributo na entrega, já faz um GET no banco e traz a lista de volta
	}

	
	
}
