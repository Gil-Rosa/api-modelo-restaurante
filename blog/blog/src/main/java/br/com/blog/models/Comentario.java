package br.com.blog.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
public class Comentario {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@JsonInclude
	@NotBlank
    @Column(nullable = false)
    private String texto;
	
	@JsonIgnore
	@NotEmpty(message = "O login deve ser informado")
	@Column(nullable = false)
	private String login;
	
	@NotEmpty(message = "O login deve ser informado")
	@Column(nullable = false)
	private String nome;
	private LocalDateTime atualizadoEm;

   @Column(nullable = false)
    private LocalDateTime criadoEm;
  
	@JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postagem_id", nullable = false)
    private Postagem postagem;
	
	@OneToMany(mappedBy = "comentario", cascade = CascadeType.ALL)
	private List<ComentRespostas> comentRespostas = new ArrayList<>();


	public List<ComentRespostas> getComentRespostas() {
		return comentRespostas;
	}

	public void setComentRespostas(List<ComentRespostas> comentRespostas) {
		this.comentRespostas = comentRespostas;
	}

	

	public LocalDateTime getAtualizadoEm() {
		return atualizadoEm;
	}

	public void setAtualizadoEm(LocalDateTime atualizadoEm) {
		this.atualizadoEm = atualizadoEm;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDateTime getCriadoEm() {
		return criadoEm;
	}

	public void setCriadoEm(LocalDateTime criadoEm) {
		this.criadoEm = criadoEm;
	}

	public Postagem getPostagem() {
		return postagem;
	}

	public void setPostagem(Postagem postagem) {
		this.postagem = postagem;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}


    
}
