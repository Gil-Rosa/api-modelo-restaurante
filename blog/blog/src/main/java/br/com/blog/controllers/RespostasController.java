package br.com.blog.controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.blog.models.ComentRespostas;
import br.com.blog.models.Comentario;
import br.com.blog.repositories.ComentarioRepository;
import br.com.blog.repositories.PostagemRepository;
import br.com.blog.repositories.RespostasRepository;
import br.com.blog.repositories.UserRepository;

@RestController
@RequestMapping("/blog")
public class RespostasController {
	@Autowired
	private RespostasRepository respostasRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ComentarioRepository comentarioRepository;
	@Autowired
	private PostagemRepository postagemRepository;

	@PostMapping("/{postId}/comentarios/resposta")
	public ResponseEntity<Object> addCommentToPost(@PathVariable Long postId, ComentRespostas respostas, String login) {
		if (!userRepository.existsByLogin(login)) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("esse login nao existe");
		}
		if (comentarioRepository.existsById(postId)) {
			Comentario comment = comentarioRepository.findById(postId).orElseThrow();

			respostas.setComentario(comment);
			respostas.setCriadoEm(LocalDateTime.now());
			respostas.setLogin(login);

			return ResponseEntity.status(HttpStatus.CREATED).body(respostasRepository.save(respostas));
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("essa postagem nao encontrada ou excluida");
		}
	}@PutMapping("/{postId}/comentarios/{commentId}/resposta/{respostaId}")
	public ResponseEntity<Object> updateCommentResponse(@PathVariable Long postId, @PathVariable Long commentId, @PathVariable Long respostaId, ComentRespostas respostas, String login) {
	    if (!userRepository.existsByLogin(login)) {
	        return ResponseEntity.status(HttpStatus.CONFLICT).body("esse login nao existe");
	    }

	    if (!postagemRepository.existsById(postId)) {
	        return ResponseEntity.status(HttpStatus.CONFLICT).body("essa postagem nao encontrada ou excluida");
	    }

	    if (!comentarioRepository.existsById(commentId)) {
	        return ResponseEntity.status(HttpStatus.CONFLICT).body("esse comentario nao existe ou foi excluido");
	    }

	    if (!respostasRepository.existsById(respostaId)) {
	        return ResponseEntity.status(HttpStatus.CONFLICT).body("essa resposta nao existe ou foi excluida");
	    }

	    ComentRespostas response = respostasRepository.findById(respostaId).orElseThrow();
	    if (!response.getLogin().equals(login)) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("voce nao tem permissao para atualizar esta resposta");
	    }

	    response.setTexto(respostas.getTexto());
	    response.setAtualizadoEm(LocalDateTime.now());

	    respostasRepository.save(response);

	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@DeleteMapping("/{postId}/comentarios/{commentId}/resposta/{respostaId}")
	public ResponseEntity<Object> deleteCommentResponse(@PathVariable Long postId, @PathVariable Long commentId, @PathVariable Long respostaId, @RequestParam(name = "login") String login) {
	    if (!userRepository.existsByLogin(login)) {
	        return ResponseEntity.status(HttpStatus.CONFLICT).body("esse login nao existe");
	    }

	    if (!postagemRepository.existsById(postId)) {
	        return ResponseEntity.status(HttpStatus.CONFLICT).body("essa postagem nao encontrada ou excluida");
	    }

	    if (!comentarioRepository.existsById(commentId)) {
	        return ResponseEntity.status(HttpStatus.CONFLICT).body("esse comentario nao existe ou foi excluido");
	    }

	    if (!respostasRepository.existsById(respostaId)) {
	        return ResponseEntity.status(HttpStatus.CONFLICT).body("essa resposta nao existe ou foi excluida");
	    }

	    ComentRespostas response = respostasRepository.findById(respostaId).orElseThrow();
	    if (!response.getLogin().equals(login)) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("voce nao tem permissao para excluir esta resposta");
	    }

	    respostasRepository.delete(response);

	    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
