package org.generation.blogPessoal.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.generation.blogPessoal.model.Post;
import org.generation.blogPessoal.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/postagens")
@CrossOrigin("*")
public class PostController {

	@Autowired
	private PostRepository repository;

	@GetMapping
	public ResponseEntity<List<Post>> getAll() {
		return ResponseEntity.ok(repository.findAll());

	}

	@GetMapping("/{id}")
	public ResponseEntity<Post> getById(@PathVariable long id) {
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}

	@GetMapping("/title/{title}")
	public ResponseEntity<List<Post>> getByTitle(@PathVariable String title) {
		return ResponseEntity.ok(repository.findAllByTitleContainingIgnoreCase(title));
	}

	@PostMapping
	public ResponseEntity<Post> post(@Valid @RequestBody Post post) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(repository.save(post));
	}

	@PutMapping
	public ResponseEntity<Post> put(@Valid @RequestBody Post post) {
		return repository.findById(post.getId())
				.map(resp -> ResponseEntity.status(HttpStatus.OK).body(repository.save(post)))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete (@PathVariable long id) {
		Optional<Post> post = repository.findById(id);
		
		if(post.isEmpty())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		
		repository.deleteById(id);
	}
}
