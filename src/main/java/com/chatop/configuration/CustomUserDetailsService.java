package com.chatop.configuration;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.chatop.dto.DBUserDTO;
import com.chatop.service.DBUserService;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private DBUserService dbUserService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		DBUserDTO dbUser = dbUserService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

		
		return new User(dbUser.getUsername(), dbUser.getPassword(), getGrantedAuthorities("ROLE_" + dbUser.getRole()));
	}

	private List<GrantedAuthority> getGrantedAuthorities(String role) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
		return authorities;
	}
}