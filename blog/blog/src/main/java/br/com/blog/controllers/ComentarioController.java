package br.com.blog.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.blog.models.Comentario;
import br.com.blog.models.Postagem;
import br.com.blog.repositories.ComentarioRepository;
import br.com.blog.repositories.PostagemRepository;
import br.com.blog.repositories.UserRepository;

@RestController
@RequestMapping("/blog")
public class ComentarioController {
	@Autowired
	private PostagemRepository postagemRepository;
	@Autowired
	UserRepository userRepository;

	@Autowired
	private ComentarioRepository comentarioRepository;

	@PostMapping("/{postId}/comentarios")
	public ResponseEntity<Object> addCommentToPost(@PathVariable Long postId, Comentario comment, String login) {

		if (!userRepository.existsByLogin(login)) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("esse login nao existe");
		}
		if (postagemRepository.existsById(postId)) {
			Postagem post = postagemRepository.findById(postId).orElseThrow();

			comment.setPostagem(post);
			comment.setCriadoEm(LocalDateTime.now());
			comment.setLogin(login);

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

	@PutMapping("/{postId}/comentarios/{commentId}")
	public ResponseEntity<Object> updateComment(@PathVariable Long postId, @PathVariable Long commentId,
			Comentario updatedComment, String login) {
		if (!userRepository.existsByLogin(login)) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("esse login nao existe");
		}

		if (!postagemRepository.existsById(postId)) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("essa postagem nao encontrada ou excluida");
		}

		if (!comentarioRepository.existsById(commentId)) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("esse comentario nao existe ou foi excluido");
		}

		Comentario comment = comentarioRepository.findById(commentId).orElseThrow();
		if (!comment.getLogin().equals(login)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("voce nao tem permissao para atualizar este comentario");
		}

		comment.setTexto(updatedComment.getTexto());
		comment.setAtualizadoEm(LocalDateTime.now());
		comentarioRepository.save(comment);

		return ResponseEntity.status(HttpStatus.OK).body(comment);
	}

	@DeleteMapping("/{postId}/comentarios/{commentId}")
	public ResponseEntity<Object> deleteComment(@PathVariable Long postId, @PathVariable Long commentId, String login) {
		if (!userRepository.existsByLogin(login)) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("esse login nao existe");
		}

		if (!postagemRepository.existsById(postId)) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("essa postagem nao encontrada ou excluida");
		}

		if (!comentarioRepository.existsById(commentId)) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("esse comentario nao existe ou foi excluido");
		}

		Comentario comment = comentarioRepository.findById(commentId).orElseThrow();
		if (!comment.getLogin().equals(login)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("voce nao tem permissao para excluir este comentario");
		}

		comentarioRepository.delete(comment);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}