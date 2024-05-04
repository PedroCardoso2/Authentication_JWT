package com.example.demo.domains;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	UserDetails findByLogin(String email);
	
	
//	 @Query("""
//             select u.role
//             from usuario u
//             where u.login = :login
//         """)
//	String findByRole(String login);
}
