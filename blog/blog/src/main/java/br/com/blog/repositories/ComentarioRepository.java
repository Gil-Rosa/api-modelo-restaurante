package br.com.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.blog.models.Comentario;
@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long>{
 
}
