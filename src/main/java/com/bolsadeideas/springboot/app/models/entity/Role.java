package com.bolsadeideas.springboot.app.models.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

   
@Entity
/* 1.- Mapemos la clase Role a la clase creada en bd -> roles. */
@Table(name = "roles",
/* 2.- Añadimos la llaves unicas como están indicadas en la tabla -> uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id","rol" */
uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id","rol"})})
public class Role implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String rol;

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name="user_id")
//	Usuario usuario;
//	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	private static final long serialVersionUID = 1L;
}
