package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.demo.domains.UsuarioRepository;

@Service
public class ServiceUsers {
	@Autowired
	private UsuarioRepository repository;
	
	public boolean isRoleUser(String emailUser) throws Exception {

		if (repository.findByLogin(emailUser) != null) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();

			if (auth != null) {
				for (GrantedAuthority granted : auth.getAuthorities()) {
					if (granted.getAuthority().equals(emailUser)) {
						return true;
					}
				}
			}

		}

		return false;
	}
}
