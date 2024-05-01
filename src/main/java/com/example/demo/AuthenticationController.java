package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domains.Usuario;
import com.example.demo.domains.UsuarioRepository;
import com.example.demo.dts.DadosLoginUsuario;
import com.example.demo.dts.DadosRegistroUsuario;
import com.example.demo.infra.security.DadosTokenJWT;
import com.example.demo.infra.security.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager manager;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private UsuarioRepository repository;

	@PostMapping("/login")
	public ResponseEntity login(@RequestBody @Valid DadosLoginUsuario dados) {
		var autheticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());

		var authetication = manager.authenticate(autheticationToken);

		var tokenJWT = tokenService.gerarToken((Usuario) authetication.getPrincipal());

		return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
	}
	
	
	@PostMapping("/register")
	public ResponseEntity registrar(@RequestBody @Valid DadosRegistroUsuario dados) {
		if(repository.findByEmail(dados.email()) != null) {
			return ResponseEntity.badRequest().body("Login já existe");
		}
		
		var passwordEncode = new BCryptPasswordEncoder().encode(dados.senha());
		
		Usuario newUsuario = new Usuario(dados);
		
		repository.save(newUsuario);
		
		String tokenJWT = tokenService.gerarToken(newUsuario);
		
		return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
	}

}
