package br.com.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.blog.models.Postagens;

@Repository
public interface PostagengsRepository extends JpaRepository<Postagens, Long>{
	public boolean existsByTitulo(String titulo);
}
