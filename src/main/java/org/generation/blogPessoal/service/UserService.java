package org.generation.blogPessoal.service;

import java.nio.charset.Charset;
import java.util.Optional;
import org.apache.commons.codec.binary.Base64;

import org.generation.blogPessoal.model.User;
import org.generation.blogPessoal.model.UserLogin;
import org.generation.blogPessoal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	public Optional<User> RegisterUser(User user) {
		Optional<User> userM = repository.findByUser(user.getUser());

		if (userM.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email já cadastrado");
		} else {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

			String passwordEncoder = encoder.encode(user.getPassword());
			user.setPassword(passwordEncoder);
			return Optional.ofNullable(repository.save(user));
		}
	}
	/*
	 * BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	 * 
	 * String passwordEncoder = encoder.encode(user.getPassword());
	 * user.setPassword(passwordEncoder);
	 * 
	 * return repository.save(user);
	 */

	public Optional<UserLogin> Login(Optional<UserLogin> user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<User> usuario = repository.findByUser(user.get().getUser());

		if (usuario.isPresent()) {
			if (encoder.matches(user.get().getPassword(), usuario.get().getPassword())) {

				String auth = user.get().getUser() + ":" + user.get().getPassword();
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodedAuth);

				user.get().setToken(authHeader);
				user.get().setName(usuario.get().getName());

				return user;
			}
		}

		return null;

	}
}
