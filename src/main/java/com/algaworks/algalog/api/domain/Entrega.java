package com.algaworks.algalog.api.domain;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import com.algaworks.algalog.api.exception.NegocioException;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Entrega {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Valid//valida o que está dentro do ciente tbm
	@ConvertGroup(from=Default.class, to=ValidationGroups.ClienteId.class)
	@NotNull
	@ManyToOne//Muitas entregas possui 1 cliente
	private Cliente cliente;
	
	//A anotação validationgroup significa que quando o cliente for validado pelo endpoint Entrega ele validará apenas
	//o atributo ID (Pq lá na classe cliente consta a mesma anotação ValidationGroups.ClienteId)
	//Quando for o endpoint de cadastro de clientes, ele utilizará automaticamente o Default.
	//Como o clienteID está com a anotação de ValidationGroups.ID ele apenas será validado quando o endpoint tiver 
	//a mesma anotação. No caso de cadastro o Validation será o Default
	
	@Valid
	@NotNull
	@Embedded //destinatario não tem tabela. Vai todo estar contido na tabela de entrega
	private Destinatario destinatario;
	
	@NotNull
	private BigDecimal taxa;
	
	@OneToMany (mappedBy = "entrega", cascade = CascadeType.ALL)  //O mapped fala quem é o dono (do lado de lá) da ocorrência. No caso o nome do atributo é ocorrência
	//O cascadetype All faz o cascateamento da persistêcia no banco de dados. Toda vez que ele é preenchido, ele persiste no banco
	private List<Ocorrencia> ocorrencias = new ArrayList<>();
	
	@Enumerated(EnumType.STRING)//diz que quer armazenar na tabela a String que representa a numeração
	private StatusEntrega status;
	
	@JsonProperty (access = Access.READ_ONLY)//Pelo usuário será apenas lido
	private OffsetDateTime dataPedido;
	
	@JsonProperty(access = Access.READ_ONLY)// só pode ser lida pelo usuário
	private OffsetDateTime dataFinalizacao;
	//OffsetDateTime é a diferença do horário no UTC e o horário no fuso local

	public Ocorrencia adicionarOcorrencia(String descricao) {
		Ocorrencia ocorrencia = new Ocorrencia();
		ocorrencia.setDescricao(descricao);
		ocorrencia.setDataRegistro(OffsetDateTime.now());
		ocorrencia.setEntrega(this);
		
		this.getOcorrencias().add(ocorrencia);
		return ocorrencia;
		
	}
	
	public void finalizar() {
		if (naoPodeSerFinalizada()) {
			throw new NegocioException("Entrega não pode ser finalizada");
		}
		
		setStatus(StatusEntrega.FINALIZADA);
		setDataFinalizacao(OffsetDateTime.now());
		
	}
	
	public boolean podeSerFinalizada() {
		return StatusEntrega.PENDENTE.equals(getStatus());
	}
	
	public boolean naoPodeSerFinalizada() {
		return !podeSerFinalizada();
	}
	
	
	

}
