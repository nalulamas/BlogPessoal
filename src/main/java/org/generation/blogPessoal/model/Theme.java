package org.generation.blogPessoal.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_theme")
public class Theme {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "O atributo Descrição é obrigatório")
	private String description;

	/*
	 * Cascade é para caso de alteração de algo em um tema, todas as postagens
	 * referentes a ele sofrerão alteração. If you change something, Cascade changes
	 * everything about this. For exemple, I deleted the theme "moreInfo", every
	 * posts linked with "moreInfo" will be deleted.
	 */

	@OneToMany(mappedBy = "theme", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("theme")
	private List<Post> post;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Post> getPost() {
		return post;
	}

	public void setPost(List<Post> post) {
		this.post = post;
	}

}
