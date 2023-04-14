package br.com.blog.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.blog.models.Papel;



public interface PapelRepository extends JpaRepository<Papel, UUID> {
	
	Papel findByNome(String role);
  

}
