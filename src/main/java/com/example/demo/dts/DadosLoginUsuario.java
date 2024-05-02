package com.example.demo.dts;

import jakarta.validation.constraints.NotBlank;


public record DadosLoginUsuario(		
		@NotBlank
		String login,
		@NotBlank
		String senha
) {

}
