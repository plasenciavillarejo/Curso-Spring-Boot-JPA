package com.bolsadeideas.springboot.app.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Grupo_Trabajo")
public class GrupoTrabajo implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 50, nullable = true)
	private String nombre;

	@DateTimeFormat
	private Date fecha_alta;

	@DateTimeFormat
	private Date fecha_baja;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "idGrupo")
	private List<UsuarioGrupoTrabajo> usuarioGrupoTrabajo;

	public Long getId() {
		return id;
	}

	public List<UsuarioGrupoTrabajo> getUsuarioGrupoTrabajo() {
		return usuarioGrupoTrabajo;
	}

	public void setUsuarioGrupoTrabajo(List<UsuarioGrupoTrabajo> usuarioGrupoTrabajo) {
		this.usuarioGrupoTrabajo = usuarioGrupoTrabajo;
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

	public Date getFecha_alta() {
		return fecha_alta;
	}

	public void setFecha_alta(Date fecha_alta) {
		this.fecha_alta = fecha_alta;
	}

	public Date getFecha_baja() {
		return fecha_baja;
	}

	public void setFecha_baja(Date fecha_baja) {
		this.fecha_baja = fecha_baja;
	}

	private static final long serialVersionUID = 1L;

}
