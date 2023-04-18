package br.com.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.blog.models.Postagem;

@Repository
public interface PostagemRepository extends JpaRepository<Postagem, Long>{
	public boolean existsByTitulo(String titulo);
	public boolean existsById(Long id);
}
