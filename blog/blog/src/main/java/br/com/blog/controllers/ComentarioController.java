package br.com.blog.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.blog.models.Comentario;
import br.com.blog.models.Postagem;
import br.com.blog.repositories.ComentarioRepository;
import br.com.blog.repositories.PostagemRepository;

@RestController
@RequestMapping("/blog")
public class ComentarioController {
	@Autowired
	private PostagemRepository postagemRepository;

	@Autowired
	private ComentarioRepository comentarioRepository;

	@PostMapping("/{postId}/comentarios")
	public ResponseEntity<Object> addCommentToPost(@PathVariable Long postId, Comentario comment) {
		if (postagemRepository.existsById(postId)) {
			Postagem post = postagemRepository.findById(postId).orElseThrow();

			comment.setPostagem(post);
			comment.setCriadoEm(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.CREATED).body(comentarioRepository.save(comment));
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("essa postagem nao encontrada ou excluida");
		}
	}

	@GetMapping("/{postagemId}/comentarios")
	public ResponseEntity<List<Comentario>> listarComentarios(@PathVariable Long postagemId) {
		Optional<Postagem> optionalPostagem = postagemRepository.findById(postagemId);
		if (optionalPostagem.isPresent()) {
			Postagem postagem = optionalPostagem.get();
			List<Comentario> comentarios = postagem.getComentarios();
			return ResponseEntity.ok(comentarios);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}