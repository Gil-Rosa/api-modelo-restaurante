package br.com.blog.config;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.blog.models.Papel;
import br.com.blog.models.Usuario;
import br.com.blog.repositories.UserRepository;

@Service
@Transactional
public class DetalheUsuarioServico implements UserDetailsService {

	private UserRepository usuarioRepository;
	
	public DetalheUsuarioServico(UserRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioRepository.findByLogin(username);
		
		if(usuario != null) {
			Set<GrantedAuthority> papeisDoUsuario = new HashSet<GrantedAuthority>();
			for(Papel papel: usuario.getPapeis()) {
				GrantedAuthority pp = new SimpleGrantedAuthority(papel.getPapel());
				papeisDoUsuario.add(pp);
			}			
			User user = new User(usuario.getLogin(), usuario.getPassword(), papeisDoUsuario);
			return user;
		} else {
			throw new UsernameNotFoundException("Usuário não encontrado");
		}
	}

}
