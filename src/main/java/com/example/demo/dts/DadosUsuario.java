package com.example.demo.dts;

import com.example.demo.domains.UserRole;

public record DadosUsuario(
		String login,
		UserRole role
		) {

}
