package br.com.blog.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.blog.models.Usuario;


@Repository
public interface UserRepository extends JpaRepository<Usuario, UUID> {


	public Usuario findByLogin(String login);
	public Optional<Usuario> findBynome(String nome);
	public Usuario findByEmail(String email);
	boolean existsByPapeisNome(String nomePapel);
	boolean existsByLogin(String login);
	       


}
