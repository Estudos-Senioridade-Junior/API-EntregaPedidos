package com.algaworks.algalog.api.domain.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteIdInput {

	@NotNull
	private Long id;
}
