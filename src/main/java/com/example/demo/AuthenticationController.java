package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dts.DadosLoginUsuario;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	
	@PostMapping("/login")
	public ResponseEntity mostrarUsuario(DadosLoginUsuario dados) {
		var authetication = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
		
		
		
		return ResponseEntity.ok("");
	}
	
	
	
	
}
