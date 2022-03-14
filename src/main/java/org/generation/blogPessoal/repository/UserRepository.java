package org.generation.blogPessoal.repository;

import java.util.List;
import java.util.Optional;

import org.generation.blogPessoal.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
	public Optional<UserModel> findByUser(String user);

	public List<UserModel> findAllByNameContainingIgnoreCase(String name);

}

/*
 * This code makes me import "List", because my UserRepository class doesn't
 * work without it. }
 */
