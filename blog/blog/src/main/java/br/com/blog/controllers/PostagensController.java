package br.com.blog.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.blog.models.Postagem;
import br.com.blog.repositories.PostagemRepository;

@RestController
@RequestMapping("/blog")
public class PostagensController {
	@Autowired
	PostagemRepository postagemRepository;
	
	@PostMapping("/user/criar")
	public ResponseEntity<Object> setPostagens(@Valid Postagem postagem) {
		if (postagemRepository.existsByTitulo(postagem.getTitulo())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("ja existe esse titulo");
		}
	postagem.setDate(LocalDateTime.now(ZoneId.of("UTC")));
		return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));
	
	}
	
	@GetMapping("/ver")
	public ResponseEntity<Page<Postagem>> getAllComidas(
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
		return ResponseEntity.status(HttpStatus.OK).body(postagemRepository.findAll(pageable));
	}

	@GetMapping("/ver/{id}")
	public ResponseEntity<Object> getOneComida(@PathVariable(value = "id") Long id) {
		Optional<Postagem> postagemOptional = postagemRepository.findById(id);
		if (!postagemOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("nao existe postagens.");
		}
		return ResponseEntity.status(HttpStatus.OK).body(postagemOptional.get());
	}
	@DeleteMapping("/user/delete/{id}")
	public ResponseEntity<Object> deleteComida(@PathVariable(value = "id") Long id) {
		Optional<Postagem> postegemOptional = postagemRepository.findById(id);
		if (!postegemOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("postagem nao existe.");
		}
		postagemRepository.delete(postegemOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body("postagem deleted successfully.");
	}

	@PutMapping("/user/atualizar/{id}")
	public ResponseEntity<Object> updateComida(@PathVariable(value = "id") Long id,
	@Valid Postagem postagem) {
	Optional<Postagem> postagensOptional = postagemRepository.findById(id);
	if (!postagensOptional.isPresent()) {
	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("postagem nao existe.");
	}
	Postagem postagemAtualizada = postagensOptional.get();
	// Mantém a data original
	LocalDateTime dataCriacao = postagemAtualizada.getDate();
	postagemAtualizada = postagem;
	postagemAtualizada.setDate(dataCriacao);
	postagemAtualizada.setId(id);
	return ResponseEntity.status(HttpStatus.OK).body(postagemRepository.save(postagemAtualizada));
	}
}
