package org.generation.blogPessoal.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.generation.blogPessoal.model.UserModel;
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
	private UserRepository userRepository;

	public Optional<UserModel> registerUser(UserModel user) {
		if (userRepository.findByUsername(user.getUsername()).isPresent())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null);
		user.setPassword(encryptPassword(user.getPassword()));
		return Optional.of(userRepository.save(user));
	}

	public Optional<UserModel> updateUser(UserModel user) {
		if (userRepository.findById(user.getId()).isPresent()) {
			Optional<UserModel> searchUser = userRepository.findByUsername(user.getUsername());
			if (searchUser.isPresent()) {
				if (searchUser.get().getId() != user.getId())
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null);
			}
			user.setPassword(encryptPassword(user.getPassword()));
			return Optional.of(userRepository.save(user));
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!", null);
	}

	public Optional<UserLogin> userLogin (Optional<UserLogin> userLogin) {	
		Optional<UserModel> user = userRepository.findByUsername(userLogin.get().getUser());
		if (user.isPresent()) {
			if (comparePassword(userLogin.get().getPassword(), user.get().getPassword())) {
				userLogin.get().setId(user.get().getId());
				userLogin.get().setName(user.get().getName());
				userLogin.get().setPicture(user.get().getPicture());
				userLogin.get().setToken(generatorBasicToken(userLogin.get().getUser(), userLogin.get().getPassword()));
                                userLogin.get().setPassword(user.get().getPassword());
				return userLogin;
			}
		}
		throw new ResponseStatusException(
				HttpStatus.UNAUTHORIZED, "Usuário ou senha inválidos!", null);
	}

	private String encryptPassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String passwordEncoder = encoder.encode(password);
		return passwordEncoder;
	}

	private boolean comparePassword(String passwordTyped, String passwordBase) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.matches(passwordTyped, passwordBase);
	}

	private String generatorBasicToken(String email, String password) {
		String structure = email + ":" + password;
		byte[] structureBase64 = Base64.encodeBase64(structure.getBytes(Charset.forName("US-ASCII")));
		return "Basic " + new String(structureBase64);
	}

}