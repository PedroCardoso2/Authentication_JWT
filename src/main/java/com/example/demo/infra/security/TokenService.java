package com.example.demo.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.example.demo.domains.Usuario;

@Service
public class TokenService {
	
	@Value("${api.security.token.secret}")
	private String CHAVE_SECRETA;
	
	public String gerarToken(Usuario usu) {
		 try {
	            var algoritmo = Algorithm.HMAC256(CHAVE_SECRETA);
	            return JWT.create()
	                    .withIssuer("UniforAPI")
	                    .withSubject(usu.getLogin())
	                    .withExpiresAt(dataExpiracao())
	                    .sign(algoritmo);
	        } catch (JWTCreationException exception){
	            throw new RuntimeException("Erro ao gerar token jwt", exception);
	        }
	}
	
	
	public String getSubject(String token) {
		try {
			var algoritmo = Algorithm.HMAC256(CHAVE_SECRETA);
			return JWT.require(algoritmo)
                    .withIssuer("UniforAPI")
                    .build()
                    .verify(token)
                    .getSubject();
		}catch(JWTCreationException ex) {
			throw new RuntimeException("Token JWT inválido !");
		}
	}

	private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
