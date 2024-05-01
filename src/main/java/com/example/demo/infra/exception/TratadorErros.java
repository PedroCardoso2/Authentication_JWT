package com.example.demo.infra.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class TratadorErros {
	
	// Entidades não encontradas
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity error404() {
		return ResponseEntity.notFound().build();
	}
	
	//Método com erro 400
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity error400(MethodArgumentNotValidException ex) {
		List<FieldError> erros = ex.getBindingResult().getFieldErrors();
		
		List<DadosErroValicao> dadosErros = erros.stream().map(DadosErroValicao::new).collect(Collectors.toList());
		
		return ResponseEntity.badRequest().body(dadosErros);
	}
	
	
	public record DadosErroValicao(String campo, String mensagem) {
		public DadosErroValicao(FieldError error) {
			this(error.getField(), error.getDefaultMessage());
		}
	}
	
	
}
