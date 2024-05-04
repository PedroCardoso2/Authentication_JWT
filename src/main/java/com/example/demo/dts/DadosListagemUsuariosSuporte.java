package com.example.demo.dts;

import com.example.demo.domains.Usuario;

public record DadosListagemUsuariosSuporte(
		String nome,
		String email,
		String senha,
		String login
		) {
	
	public DadosListagemUsuariosSuporte(Usuario usuario) {
		this(usuario.getNome(), usuario.getEmail(), usuario.getSenha(), usuario.getLogin());
	}

}
