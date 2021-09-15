package com.bolsadeideas.springboot.app.models.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.springboot.app.models.dao.IUsuarioDao;
import com.bolsadeideas.springboot.app.models.entity.Role;
import com.bolsadeideas.springboot.app.models.entity.Usuario;

@Service("jpaUserDetailService")
public class JPAUserDetailService implements UserDetailsService{

	@Autowired
	private IUsuarioDao usuarioDao;
	
	private Logger logger = LoggerFactory.getLogger(JPAUserDetailService.class);
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
	/* 1.- Obtenemos el usuario. */	
		Usuario usuario = usuarioDao.findByUserName(username);
		
		if (usuario == null) {
			logger.error("Error Login: no existe el usuario " + username);
			throw new UsernameNotFoundException("Username: " + username + "no existe en el sistema !!");
		}
		
	/* 2.- Obtenemos sus roles mediante una Lista */	
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		for(Role role : usuario.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getRol()));
		}
		
		if (authorities.isEmpty()) {
			logger.error("Error Login: usuario " + username + " no tiene roles asigandos");
			throw new UsernameNotFoundException("Error Login: usuario " + username + " no tiene roles asigandos");
		}	
		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
	}

}
