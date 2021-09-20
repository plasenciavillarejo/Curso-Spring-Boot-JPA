package com.bolsadeideas.springboot.app.models.dao;

import java.lang.annotation.Native;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.bolsadeideas.springboot.app.models.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long>{

	public Usuario findByUsername(String username);
	
	
	@Query(value = "select * from users where username = :username and password = :password",
			nativeQuery = true)
	List<Usuario> findByUsernameAndPassword(@Param("username") String username,
									 		@Param("password") String password) throws Exception;
}	
