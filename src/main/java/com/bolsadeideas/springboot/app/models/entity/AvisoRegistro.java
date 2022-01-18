package com.bolsadeideas.springboot.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Aviso_Registro")
public class AvisoRegistro implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Date fechaAviso1;

	private Date fechaAviso2;

	private Date fechaAviso3;

	private int periodo;

	private Date fechaInicio;

	private Date fechaCaducidad;

	@OneToOne
	@JoinColumn(name = "idApunteRegistro")
	private ApunteRegistroType idApunteRegistro;

	public ApunteRegistroType getIdApunteRegistro() {
		return idApunteRegistro;
	}

	public void setIdApunteRegistro(ApunteRegistroType idApunteRegistro) {
		this.idApunteRegistro = idApunteRegistro;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechaAviso1() {
		return fechaAviso1;
	}

	public void setFechaAviso1(Date fechaAviso1) {
		this.fechaAviso1 = fechaAviso1;
	}

	public Date getFechaAviso2() {
		return fechaAviso2;
	}

	public void setFechaAviso2(Date fechaAviso2) {
		this.fechaAviso2 = fechaAviso2;
	}

	public Date getFechaAviso3() {
		return fechaAviso3;
	}

	public void setFechaAviso3(Date fechaAviso3) {
		this.fechaAviso3 = fechaAviso3;
	}

	public int getPeriodo() {
		return periodo;
	}

	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(Date fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

	private static final long serialVersionUID = 1L;

}
