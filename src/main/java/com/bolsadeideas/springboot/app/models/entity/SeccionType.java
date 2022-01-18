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
@Table(name ="seccionType")
public class SeccionType implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	
	@NotEmpty
	@Column(length = 250)
	public String titulo;
	
	@OneToMany(mappedBy = "seccionType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<CampoType> campotype;
	
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

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public List<CampoType> getCampotype() {
		return campotype;
	}

	public void setCampotype(List<CampoType> campotype) {
		this.campotype = campotype;
	}

	
	
}
