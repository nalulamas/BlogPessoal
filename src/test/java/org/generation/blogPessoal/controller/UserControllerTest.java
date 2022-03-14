package org.generation.blogPessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.generation.blogPessoal.model.UserModel;
import org.generation.blogPessoal.repository.UserRepository;
import org.generation.blogPessoal.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@BeforeAll
	void start() {
		
		userRepository.deleteAll();
	}
	@Test
	@Order(1)
	@DisplayName("Cadastrar um usuário")
	public void mostCreateAnUser() {
		HttpEntity<UserModel> request = new HttpEntity<UserModel>(new UserModel( 0L,"Paulo Antunes", "Paulo_Antunes@email.com", "123456"));
		
		ResponseEntity<UserModel> answer = testRestTemplate.exchange("/users/register", HttpMethod.POST,request, UserModel.class);
		
		assertEquals(HttpStatus.CREATED, answer.getStatusCode());
		assertEquals(request.getBody().getName(), answer.getBody().getName());
		assertEquals(request.getBody().getUser(), answer.getBody().getUser());
	}
	@Test
	@Order(2)
	@DisplayName("Não deve permitir duplicação do usuário.")
	public void dontDuplicateAnUser() {
		
		userService.registerUser(new UserModel(0L, "Maria Silva", "maria@email.com","12345"));
		
		HttpEntity<UserModel> request = new HttpEntity<UserModel>(new UserModel(0L,"Paulo Antunes", "Paulo_Antunes@email.com", "123456"));
		
		ResponseEntity<UserModel> answer = testRestTemplate.exchange("/users/register", HttpMethod.POST, request, UserModel.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, answer.getStatusCode());
	}	
	@Test
	@Order(3)
	@DisplayName("Alterar um usuario")
	public void mostChangeUser() {
		
		Optional <UserModel> userCreate = userService.registerUser(new UserModel (0L, "Juliana Andrews","juliana@email.com", "juliana123"));		
		
		UserModel userUpdate = new UserModel(userCreate.get().getId(),
				"Juliana Andrws Ramos", "juliana_ramos@email.com", "juliana123");
		
		HttpEntity<UserModel> request = new HttpEntity<UserModel>(userUpdate);
		
		ResponseEntity<UserModel> answer = testRestTemplate.exchange("/users/register", HttpMethod.POST, request, UserModel.class);
		
		assertEquals(HttpStatus.CREATED, answer.getStatusCode());
		assertEquals(userUpdate.getName(), answer.getBody().getName());
		assertEquals(userUpdate.getUser(), answer.getBody().getUser()); 
		
	} 

	@Test
	@Order(4)
	@DisplayName("Listar todos as Postagens")
	public void mostChangeAllPosts() {
		userService.registerUser(new UserModel(0L, "Sabrina Sanches", "sabrina@email.com", 
				"sabrina123"));			
		
		userService.registerUser(new UserModel (0L, "Ricardo Marques", "ricardo@email.com","ricardo123"));
		
		ResponseEntity<String> answer = testRestTemplate
				.withBasicAuth("sabrina@email.com", "sabrina123")
				.exchange("/posts", HttpMethod.GET, null, String.class);
		
		assertEquals(HttpStatus.OK, answer.getStatusCode());
	}
}
