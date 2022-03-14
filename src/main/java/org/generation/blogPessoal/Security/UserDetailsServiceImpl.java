package org.generation.blogPessoal.Security;

import java.util.Optional;

import org.generation.blogPessoal.model.User;
import org.generation.blogPessoal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class UserDetailsServiceImpl implements UserDetailsService{	
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional <User> optional = userRepository.findByUser(userName);		
		if(optional.isPresent()) {
			return new UserDetailsImpl(optional.get());
		}else {
			throw new UsernameNotFoundException("Usuário não encontrado.");
		}
	}

}
