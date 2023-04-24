package br.com.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.blog.models.ComentRespostas;
@Repository
public interface RespostasRepository extends JpaRepository<ComentRespostas, Long> {

}
