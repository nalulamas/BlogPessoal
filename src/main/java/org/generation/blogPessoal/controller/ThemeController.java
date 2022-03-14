package org.generation.blogPessoal.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.generation.blogPessoal.model.Theme;
import org.generation.blogPessoal.repository.ThemeRepository;
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
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/theme")
public class ThemeController {
	
	@Autowired
	private ThemeRepository repository;

	@GetMapping
	public ResponseEntity<List<Theme>> getAll() {
		return ResponseEntity.ok(repository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Theme> getById(@PathVariable long id) {
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}
	
	@GetMapping("/description/{description}")
	public ResponseEntity<List<Theme>> getByTitle(@PathVariable String description){
		return ResponseEntity.ok(repository.findAllByDescriptionContainingIgnoreCase(description));
	}	

	@PostMapping
	public ResponseEntity<Theme> post(@Valid @RequestBody Theme theme) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(repository.save(theme));
	}

	@PutMapping
	public ResponseEntity<Theme> put(@Valid @RequestBody Theme theme) {
		return repository.findById(theme.getId())
				.map(resp -> ResponseEntity.status(HttpStatus.CREATED).body(repository.save(theme)))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {
		Optional<Theme> theme = repository.findById(id);
		
		if(theme.isEmpty())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		
		repository.deleteById(id);

	}
}
