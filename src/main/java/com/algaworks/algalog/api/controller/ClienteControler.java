package com.algaworks.algalog.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algalog.api.domain.Cliente;
import com.algaworks.algalog.api.repository.ClienteRepository;
import com.algaworks.algalog.api.service.CatalogoClienteService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/clientes") // Já instancia o /Clientes para todos os métodos
public class ClienteControler {//receber a requisição, chamar outras classes e regras e devolver resposta
	
	
	private ClienteRepository clienteRepository;
	private CatalogoClienteService catalogoClienteService;

	@GetMapping
	public List<Cliente> listar() {
		
		return clienteRepository.findAll();
//		clienteRepository.findByNome("Patrick");
//		return clienteRepository.findByNomeContaining("du");
				
	}
	
	@GetMapping("/{clienteId}")
	public ResponseEntity<Cliente> buscar (@PathVariable Long clienteId) { // o mesmo nome de variável do path tem que ser o do argumento
		return clienteRepository.findById(clienteId)
//				.map(cliente -> ResponseEntity.ok(cliente))
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
			
		
//		Optional <Cliente> cliente = clienteRepository.findById(clienteId);
//		if (cliente.isPresent()) {
//			return ResponseEntity.ok(cliente.get());
//		} else {
//			return ResponseEntity.notFound().build();
//		}
		
		
		
		//Dentro do optional pode ter alguma coisa ou não. O or Else ele passa o que esta no argumento caso nao tenha nada no Optional, no caso null
		//com o Response Entity pode manipular melhor a resposta. Inclusive o código de resposta
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED) //para passar o código 201 de created
	public Cliente adicionar(@Valid @RequestBody Cliente cliente) {//RequestBody transforma o Json da requisiçao em um corpo Java
		return catalogoClienteService.salvar(cliente);//Valid - Para o Bean validar de acordo com as restrições que coloquei em clientes
				
	}
	
	@PutMapping ("/{clienteId}")
	public ResponseEntity<Cliente> Atualizar (@Valid @PathVariable Long clienteId,
			@RequestBody Cliente cliente){
		if (!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		} 
			cliente.setId(clienteId);
			cliente = catalogoClienteService.salvar(cliente);
			
			return ResponseEntity.ok(cliente);		
	}
	
	@DeleteMapping("/{clienteId}")
	public ResponseEntity <Void> Remover (@PathVariable Long clienteId){ //Void significa que o corpo da resposta não vai existir - APenas o código HTTP
		if (!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();//retorna o 404
		}
		
		 catalogoClienteService.excluir(clienteId);
		
		return ResponseEntity.noContent().build();//Retornar código 204 - Excluído com sucesso
			
	}
	
	
	
	
}

