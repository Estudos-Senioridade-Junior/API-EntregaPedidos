package com.algaworks.algalog.api.excepthandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algalog.api.exception.EntidadeNaoEncontradaException;
import com.algaworks.algalog.api.exception.NegocioException;


import lombok.AllArgsConstructor;

@AllArgsConstructor
@ControllerAdvice //Trata as exceptions de maneira global. Sempre que um controller lançar uma exception, ele utilizará o padrão desta classe
public class ApiExceptHandler extends ResponseEntityExceptionHandler{
	
	
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<Problema.Campo> campos = new ArrayList<>();
		
		for (ObjectError erro : ex.getBindingResult().getAllErrors()) {
			String nome = ((FieldError) erro).getField();
			String mensagem = messageSource.getMessage(erro, LocaleContextHolder.getLocale());//erro.getDefaultMessage();
			//O metodo acima messageSource transforma a mensagem em portugues, de acordo com a linguagem local
			//Ocorre que meu método anterior (que está depois do comentário) já estava em português
			//acredito que seja alguma atualizaçao lançada pós gravação de curso
			campos.add(new Problema.Campo(nome, mensagem));
			
		}
		
				
		Problema problema = new Problema();
		problema.setStatus(status.value());
		problema.setDataHora(OffsetDateTime.now());
		problema.setTitulo("Um ou mais campos estão invalidos. Faça o preenchimento correto e tente novamente");
		problema.setCampos(campos);
		
		return handleExceptionInternal(ex, problema, headers, status, request);
	}
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)//Caso a execção negócio exception for chamada em qualquer lugar, este é o método responsável por tratar a exceção
	public ResponseEntity<Object> handleNegocio (EntidadeNaoEncontradaException ex, WebRequest request){
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		Problema problema = new Problema();
		problema.setStatus(status.value());
		problema.setDataHora(OffsetDateTime.now());
		problema.setTitulo(ex.getMessage());
				
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(NegocioException.class)//Caso a execção negócio exception for chamada em qualquer lugar, este é o método responsável por tratar a exceção
	public ResponseEntity<Object> handleNegocio (NegocioException ex, WebRequest request){
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		Problema problema = new Problema();
		problema.setStatus(status.value());
		problema.setDataHora(OffsetDateTime.now());
		problema.setTitulo(ex.getMessage());
				
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

}