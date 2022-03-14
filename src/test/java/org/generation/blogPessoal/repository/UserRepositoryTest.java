package org.generation.blogPessoal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.generation.blogPessoal.model.UserModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserRepositoryTest {
	
	@Autowired
	private UserRepository userRepository;
	
	@BeforeAll
	void start() {
		userRepository.deleteAll();
		userRepository.save(new UserModel (0L, "Maria","maria@email.com","123456"));
		userRepository.save(new UserModel (0L, "Jose","jose@email.com","123456"));
		userRepository.save(new UserModel (0L, "Ana","ana@email.com","123456"));
		userRepository.save(new UserModel (0L, "Fernando","fernando@email.com","123456"));
	}
	
	@Test
	@DisplayName("Retorna 1 usuario")
	public void mostReturnAnUser() {
		Optional<UserModel> user = userRepository.findByUsername("jose@email.com");
		assertTrue(user.get().getUsername().equals("jose@email.com"));
	}
	
	@Test
	@DisplayName("Retorna 3 Usuarios")
	public void mostReturnThreeUsers() {
		List<UserModel> userList = userRepository.findAllByNameContainingIgnoreCase("Silva");
		assertEquals(3, userList.size());
		assertTrue(userList.get(0).getName().equals("José da Silva"));
		assertTrue(userList.get(1).getName().equals("Maria da Silva"));
		assertTrue(userList.get(2).getName().equals("Ana da Silva"));
		
	}

}
