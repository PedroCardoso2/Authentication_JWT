package com.example.demo.dts;

import java.util.Date;

import com.example.demo.domains.UserRole;

import jakarta.validation.constraints.NotBlank;

public record DadosRegistroUsuario(
		@NotBlank
		String nome,
		@NotBlank
		String email,
		@NotBlank
		String senha,
		@NotBlank
		Date date,
		@NotBlank
		UserRole role
) {

}
