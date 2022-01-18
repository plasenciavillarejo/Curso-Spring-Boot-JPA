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
@Table(name = "anexoType")
public class AnexoType implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	@NotEmpty
	@Column(length = 80)
	public String nombre;

	@Column(length = 50)
	public String identificador;

	@SuppressWarnings("rawtypes")
	@NotEmpty
	@Column(length = 50)
	private Enum validez;

	@SuppressWarnings("rawtypes")
	@NotEmpty
	@Column(length = 100)
	public Enum tipoDocumento;

	@NotEmpty
	@Column(length = 512)
	public String hash;

	@Column(length = 256)
	public String tipoMime;

	public Long tamanioFichero;

	@Column(length = 100)
	public String anexo;

	@NotEmpty
	@Column(length = 50)
	public Enum tipoFirma;

	@Column(length = 80)
	public String nombreFirma;

	@Column(length = 512)
	public String hashFirma;

	@Column(length = 256)
	public String tipoMimeFirma;

	public Long tamanioFicheroFirma;

	@Column(length = 100)
	public String firma;

	@Column(length = 100)
	public String anexoCSV;

	@Column(length = 100)
	public String nombreCSV;

	@Column(length = 100)
	public String hashCSV;

	@Column(length = 100)
	public String tipomimeCSV;

	public Long tamanioFicheroCSV;

	@Column(length = 100)
	public String codigoCSV;

	@Column(length = 50)
	public String observaciones;

	@ManyToOne(fetch = FetchType.LAZY)
	public ApunteRegistroType apunteRegistroType;

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

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getObservaciones() {
		return observaciones;
	}

	@SuppressWarnings("rawtypes")
	public void setValidez(Enum validez) {
		this.validez = validez;
	}

	@SuppressWarnings("rawtypes")
	public Enum getTipoDocumento() {
		return tipoDocumento;
	}

	@SuppressWarnings("rawtypes")
	public void setTipoDocumento(Enum tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getTipoMime() {
		return tipoMime;
	}

	public void setTipoMime(String tipoMime) {
		this.tipoMime = tipoMime;
	}

	public Long getTamanioFichero() {
		return tamanioFichero;
	}

	public void setTamanioFichero(Long tamanioFichero) {
		this.tamanioFichero = tamanioFichero;
	}

	public String getAnexo() {
		return anexo;
	}

	public void setAnexo(String anexo) {
		this.anexo = anexo;
	}

	public Enum getTipoFirma() {
		return tipoFirma;
	}

	public void setTipoFirma(Enum tipoFirma) {
		this.tipoFirma = tipoFirma;
	}

	public String getNombreFirma() {
		return nombreFirma;
	}

	public void setNombreFirma(String nombreFirma) {
		this.nombreFirma = nombreFirma;
	}

	public String getHashFirma() {
		return hashFirma;
	}

	public void setHashFirma(String hashFirma) {
		this.hashFirma = hashFirma;
	}

	public String getTipoMimeFirma() {
		return tipoMimeFirma;
	}

	public void setTipoMimeFirma(String tipoMimeFirma) {
		this.tipoMimeFirma = tipoMimeFirma;
	}

	public Long getTamanioFicheroFirma() {
		return tamanioFicheroFirma;
	}

	public void setTamanioFicheroFirma(Long tamanioFicheroFirma) {
		this.tamanioFicheroFirma = tamanioFicheroFirma;
	}

	public String getFirma() {
		return firma;
	}

	public void setFirma(String firma) {
		this.firma = firma;
	}

	public String getAnexoCSV() {
		return anexoCSV;
	}

	public void setAnexoCSV(String anexoCSV) {
		this.anexoCSV = anexoCSV;
	}

	public String getNombreCSV() {
		return nombreCSV;
	}

	public void setNombreCSV(String nombreCSV) {
		this.nombreCSV = nombreCSV;
	}

	public String getHashCSV() {
		return hashCSV;
	}

	public void setHashCSV(String hashCSV) {
		this.hashCSV = hashCSV;
	}

	public String getTipomimeCSV() {
		return tipomimeCSV;
	}

	public void setTipomimeCSV(String tipomimeCSV) {
		this.tipomimeCSV = tipomimeCSV;
	}

	public Long getTamanioFicheroCSV() {
		return tamanioFicheroCSV;
	}

	public void setTamanioFicheroCSV(Long tamanioFicheroCSV) {
		this.tamanioFicheroCSV = tamanioFicheroCSV;
	}

	public String getCodigoCSV() {
		return codigoCSV;
	}

	public void setCodigoCSV(String codigoCSV) {
		this.codigoCSV = codigoCSV;
	}

	public String getValidez() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public ApunteRegistroType getApunteRegistroType() {
		return apunteRegistroType;
	}

	public void setApunteRegistroType(ApunteRegistroType apunteRegistroType) {
		this.apunteRegistroType = apunteRegistroType;
	}

	private static final long serialVersionUID = 1L;

}
