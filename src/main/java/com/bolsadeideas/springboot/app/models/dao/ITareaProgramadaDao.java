package com.bolsadeideas.springboot.app.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bolsadeideas.springboot.app.models.entity.Shedlock;

public interface ITareaProgramadaDao extends JpaRepository<Shedlock, String>{

	@Query(value = "select c from #{#entityName} c")
	public Shedlock buscarTareaBBDD();
	
	@Modifying
	@Query("delete from #{#entityName} b where b.name like'%:name'")
	public void borrarEntidad(@Param("name") String name);
	
}
