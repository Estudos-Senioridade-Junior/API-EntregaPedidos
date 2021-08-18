package com.algaworks.algalog.api.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
public class Cliente {

	@NotNull(groups=ValidationGroups.ClienteId.class)
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	//A anotação validationgroup significa que quando o cliente for validado pelo endpoint Entrega ele validará apenas
		//o atributo ID (Pq lá na classe cliente consta a mesma anotação ValidationGroups.ClienteId)
		//Quando for o endpoint de cadastro de clientes, ele utilizará automaticamente o Default.
		//Como o clienteID está com a anotação de ValidationGroups.ID ele apenas será validado quando o endpoint tiver 
		//a mesma anotação. No caso de cadastro o Validation será o Default
	
	@NotBlank
	@Size(max = 60)
	private String nome;
	
	@NotBlank
	@Email
	@Size (max=255)
	private String email;
	
	@NotBlank
	@Size (max=20)
	private String telefone;
	
}
