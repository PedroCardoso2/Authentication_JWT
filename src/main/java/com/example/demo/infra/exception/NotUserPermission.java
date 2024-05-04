package com.example.demo.infra.exception;

public class NotUserPermission extends RuntimeException{
	
	public NotUserPermission() {
		super("Usuário não permitido");
	}
	
	public NotUserPermission(String msg) {
		super(msg);
	}
}
