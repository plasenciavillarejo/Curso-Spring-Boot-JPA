package com.bolsadeideas.springboot.app.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "campoType")
public class CampoType implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	
	@NotEmpty
	@Column(length = 400)
	public String nombre;
	
	@NotEmpty
	@Column(length = 4000)
	public String valor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	public SeccionType seccionType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	public FormularioType formularioType;
	
	public FormularioType getFormularioType() {
		return formularioType;
	}

	public void setFormularioType(FormularioType formularioType) {
		this.formularioType = formularioType;
	}

	private static final long serialVersionUID = 1L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public SeccionType getSeccionType() {
		return seccionType;
	}

	public void setSeccionType(SeccionType seccionType) {
		this.seccionType = seccionType;
	}

	
	
}
