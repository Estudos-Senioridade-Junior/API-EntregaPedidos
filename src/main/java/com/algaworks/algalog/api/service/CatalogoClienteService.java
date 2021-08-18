package com.algaworks.algalog.api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algalog.api.domain.Cliente;
import com.algaworks.algalog.api.exception.NegocioException;
import com.algaworks.algalog.api.repository.ClienteRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CatalogoClienteService {
	

	private ClienteRepository clienteRepository;
	
	public Cliente buscar (Long id) {
		return clienteRepository.findById(id)
				.orElseThrow(() -> new NegocioException("Cliente não encontrado"));
		
		//OrElseThrow significa - O findbyId retorna um optional. Ele fala: Pega o que tem dentro deste optional e 
		//atribui a cliente. Caso ele esteja vazio, lance uma exceção
	}
	
	
	@Transactional //Se algo der errado, todas as alterações são descartadas. Ou tudo ou nada
	public Cliente salvar(Cliente cliente) {
		boolean emailEmUso = clienteRepository.findByEmail(cliente.getEmail())
				.stream()
				.anyMatch(clienteExistente -> !clienteExistente.equals(cliente)); //Match retorna true ou false
				//clienteexistente antes da lambda é o resultado do cliente da pesquisa do findbyemail
		
		if (emailEmUso) {
			throw new NegocioException("Já existe um cliente cadastrado com este e-mail");
		}
		return clienteRepository.save(cliente);
	}
	
	@Transactional
	public void excluir (Long idCliente) {
		clienteRepository.deleteById(idCliente);
	}
	
	
	

}

