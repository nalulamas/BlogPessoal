package org.generation.blogPessoal.Security;

import java.util.Collection;
import java.util.List;

import org.generation.blogPessoal.model.UserModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	private String userName;
	private String password;
	private List<GrantedAuthority> authorities;
	
	public UserDetailsImpl(UserModel user) {
		this.userName = user.getUser();
		this.password = user.getPassword();
	}
	
	/*
	 * construtor vazio
	 * empty constructor
	 */
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return authorities;
	}

	@Override
	public String getPassword() {
		
		return password;
	}

	@Override
	public String getUsername() {
		
		return userName;
	}

	/*
	 * os metodos foram alterados para "true", por boas práticas
	 * eles não serão utilizados, mas darão,caso necessário, o retorno esperado.
	 */
	
	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		return true;
	}

}
