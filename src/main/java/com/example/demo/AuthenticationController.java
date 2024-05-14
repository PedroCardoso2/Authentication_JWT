package com.example.demo;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
// @SecurityRequirement(name = "bearer-key")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager manager;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private  UsuarioRepository repository;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid DadosLoginUsuario dados) throws Exception {
		Usuario usuario = (Usuario) repository.findByLogin(dados.login());
		if (usuario != null) {
			var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
			
			

			var authentication = manager.authenticate(authenticationToken);

		

			var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

			return ResponseEntity.ok(new DadosTokenJWT(tokenJWT) );
		} else {
			throw new AuthenticationException("Usuário não encontrado");
		}
	}

	@PostMapping("/register")
	public ResponseEntity registrar(@RequestBody @Valid DadosRegistroUsuario dados) {
		if (repository.findByLogin(dados.email()) != null) {
			return ResponseEntity.badRequest().body("Login já existe");
		}

		var passwordEncode = new BCryptPasswordEncoder().encode(dados.senha());

		DadosRegistroUsuario dadosCriptografados = new DadosRegistroUsuario(dados.nome(), dados.email(), passwordEncode,
				dados.date(), dados.role());

		Usuario newUsuario = new Usuario(dadosCriptografados);

		repository.save(newUsuario);

		String tokenJWT = tokenService.gerarToken(newUsuario);

		return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
	}

	
}
