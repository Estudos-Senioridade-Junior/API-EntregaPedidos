package com.algaworks.algalog.api.domain.input;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntregaInput {//Classe responsável pela entrada de dados
	//Serve para isolar a classe responsável pela entidade da classe responsável pela
	//entrada e saída de dados

	@Valid
	@NotNull
	private ClienteIdInput cliente;
	
	@Valid
	@NotNull
	private DestinatarioInput destinatario;
	
	@NotNull
	private BigDecimal taxa;
	
	
	
}
