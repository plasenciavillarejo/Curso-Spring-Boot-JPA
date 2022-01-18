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
@Table(name = "controlPeticionDiaria")
public class ControlPeticionDiaria implements Serializable {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	
	@NotEmpty
	public String nuRegistro;
	
	@NotEmpty
	@Column(length = 14)
	public String timestampPresentadoDesde;
	
	@NotEmpty
	@Column(length = 14)
	public String timestampPresentadoHasta;
	
	@ManyToOne(fetch = FetchType.LAZY)
	public ApunteRegistroType apunteRegistroType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNuRegistro() {
		return nuRegistro;
	}

	public void setNuRegistro(String nuRegistro) {
		this.nuRegistro = nuRegistro;
	}

	public String getTimestampPresentadoDesde() {
		return timestampPresentadoDesde;
	}

	public void setTimestampPresentadoDesde(String timestampPresentadoDesde) {
		this.timestampPresentadoDesde = timestampPresentadoDesde;
	}

	public String getTimestampPresentadoHasta() {
		return timestampPresentadoHasta;
	}

	public void setTimestampPresentadoHasta(String timestampPresentadoHasta) {
		this.timestampPresentadoHasta = timestampPresentadoHasta;
	}

	public ApunteRegistroType getApunteRegistroType() {
		return apunteRegistroType;
	}

	public void setApunteRegistroType(ApunteRegistroType apunteRegistroType) {
		this.apunteRegistroType = apunteRegistroType;
	}
	
	private static final long serialVersionUID = 1L;
	
}
