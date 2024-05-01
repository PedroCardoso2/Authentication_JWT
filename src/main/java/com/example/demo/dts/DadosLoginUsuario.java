package com.example.demo.dts;

import java.util.Date;


import jakarta.validation.constraints.NotBlank;


public record DadosLoginUsuario(		
		@NotBlank
		String email,
		@NotBlank
		String senha
) {

}
