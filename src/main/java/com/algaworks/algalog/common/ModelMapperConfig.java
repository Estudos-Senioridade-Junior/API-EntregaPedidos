package com.algaworks.algalog.common;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration//Declara que é um componente spring com função de definição de beans
public class ModelMapperConfig {
	
	@Bean // - Significa que o Spring passará a ser gerenciado pelo Spring.
	//Tudo pq o ModelMapper é uma biblioteca externa e assim não pode ser usado diretamente no código
	public ModelMapper modelMapper () {
		return new ModelMapper();
	}

}
