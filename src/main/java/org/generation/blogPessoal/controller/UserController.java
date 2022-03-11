package org.generation.blogPessoal.controller;

import java.util.Optional;
import org.generation.blogPessoal.model.User;
import org.generation.blogPessoal.model.UserLogin;
import org.generation.blogPessoal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

	@Autowired
	private UserService userService;

	/*
	 * Metodo logar
	 */

	@PostMapping("/login")
	public ResponseEntity<UserLogin> Autentication(@RequestBody Optional<UserLogin> user) {
		return userService.Login(user).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}

	/*
	 * Metodo cadastrar
	 */

	@PostMapping("/register")
	public ResponseEntity<User> Post(@RequestBody User user) {
		return userService.RegisterUser(user).map(resp -> ResponseEntity.status(201).body(resp))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());

		/*
		 * ResponseEntity.status(HttpStatus.CREATED)
		 * .body(userService.RegisterUser(user));
		 */
	}
}
