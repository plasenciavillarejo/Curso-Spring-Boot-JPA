package com.bolsadeideas.springboot.app.models.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "formularioType")
public class FormularioType implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	@NotEmpty
	@Column(length = 250)
	public String titulo;

	@OneToMany(mappedBy = "formularioType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<CampoType> campoType;

	@OneToMany(mappedBy = "formularioType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<SeccionType> seccionType;

	@NotEmpty
	@Column(length = 250)
	public String plazos;

	@NotEmpty
	@Column(length = 50)
	public Enum silencioAdministrativo;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public ApunteRegistroType apunteRegistroType;

	private static final long serialVersionUID = 1L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public List<CampoType> getCampoType() {
		return campoType;
	}

	public void setCampoType(List<CampoType> campoType) {
		this.campoType = campoType;
	}

	public List<SeccionType> getSeccionType() {
		return seccionType;
	}

	public void setSeccionType(List<SeccionType> seccionType) {
		this.seccionType = seccionType;
	}

	public String getPlazos() {
		return plazos;
	}

	public void setPlazos(String plazos) {
		this.plazos = plazos;
	}

	public Enum getSilencioAdministrativo() {
		return silencioAdministrativo;
	}

	public void setSilencioAdministrativo(Enum silencioAdministrativo) {
		this.silencioAdministrativo = silencioAdministrativo;
	}

	public ApunteRegistroType getApunteRegistroType() {
		return apunteRegistroType;
	}

	public void setApunteRegistroType(ApunteRegistroType apunteRegistroType) {
		this.apunteRegistroType = apunteRegistroType;
	}

}
