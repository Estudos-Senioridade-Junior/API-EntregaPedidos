package com.algaworks.algalog.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algalog.api.domain.Entrega;
import com.algaworks.algalog.api.domain.EntregaModel;
import com.algaworks.algalog.api.domain.input.EntregaInput;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component //Inidica que será gerenciado pelo Spring
public class EntregaAssembler {//responsável pela conversão de objetos
	
	private ModelMapper modelMapper;
	
	public EntregaModel toModel (Entrega entrega) {
		return modelMapper.map(entrega, EntregaModel.class);
		
		//O Model Mapper faz tudo isso que está aqui embaixo.
		//Basta colocar a dependência de ModelMapper no POM
		
//		EntregaModel entregaModel = new EntregaModel();
//		entregaModel.setId(entrega.getCliente().getId());
//		entregaModel.setNomeCliente(entrega.getCliente().getNome());
//		entregaModel.setDestinatarioModel(new DestinatarioModel());
//		entregaModel.getDestinatarioModel().setNome(entrega.getDestinatario().getNome());
//		entregaModel.getDestinatarioModel().setLogradouro(entrega.getDestinatario().getLogradouro());
//		entregaModel.getDestinatarioModel().setBairro(entrega.getDestinatario().getBairro());
//		entregaModel.getDestinatarioModel().setComplemento(entrega.getDestinatario().getComplemento());
//		entregaModel.getDestinatarioModel().setNumero(entrega.getDestinatario().getNumero());
//		entregaModel.setTaxa(entrega.getTaxa());
//		entregaModel.setStatus(entrega.getStatus());
//		entregaModel.setDataPedido(entrega.getDataPedido());
//		entregaModel.setDataFinalizacao(entrega.getDataFinalizacao());
}
	
	public List<EntregaModel> toCollectionModel (List<Entrega> entregas){
		return entregas.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
	
	public Entrega toEntity (EntregaInput entregaInput) {
		return modelMapper.map(entregaInput, Entrega.class);
	}
}
