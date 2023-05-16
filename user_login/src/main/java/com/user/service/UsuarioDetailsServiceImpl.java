package com.user.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.user.dao.ILUserDAO;
import com.user.dto.LUsers;

@Service
public class UsuarioDetailsServiceImpl implements UserDetailsService {

	private ILUserDAO iSUserDAO;

	public UsuarioDetailsServiceImpl(ILUserDAO iUsuarioDAO) {
		this.iSUserDAO = iUsuarioDAO;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LUsers luser = iSUserDAO.findByUsername(username);
		if (luser == null) {
			throw new UsernameNotFoundException(username);
		}
		return new User(luser.getUsername(), luser.getPassword(), mapRolesToAuthorities(luser.getRole()));
	}

	private Collection<GrantedAuthority> mapRolesToAuthorities(String rol) {
		ArrayList<String> roles = new ArrayList<String>();
		roles.add(rol);
		return roles.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
	}

}
