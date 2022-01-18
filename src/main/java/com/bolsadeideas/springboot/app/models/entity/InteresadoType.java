package com.bolsadeideas.springboot.app.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "interesadoType")
public class InteresadoType implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	@SuppressWarnings("rawtypes")
	@Column(length = 100)
	public Enum tipoIdentificadorInteresado;

	@Column(length = 17)
	public String identificadorInteresado;

	@Column(length = 30)
	public String nombreInteresado;

	@Column(length = 30)
	public String primerApellidoInteresado;

	@Column(length = 30)
	public String segundoApellidoInteresado;

	@Column(length = 80)
	public String razonSocialInteresado;

	@Column(length = 4)
	public String cdPaisInteresado;

	@Column(length = 2)
	public String cdProvinciaInteresado;

	@Column(length = 5)
	public String cdMunicipioInteresado;

	@Column(length = 160)
	public String direccionInteresado;

	@Column(length = 5)
	public String codigoPostalInteresado;

	@Column(length = 160)
	public String mailInteresado;

	@Column(length = 20)
	public String telefonoInteresado;

	@Column(length = 100)
	public Enum canalNotificacionInteresado;

	@Column(length = 160)
	public String direccionElectronicaInteresado;

	@Column(length = 50)
	public Enum tipoIdentificadorRepresentante;

	@Column(length = 17)
	public String identificadorRepresentante;

	@Column(length = 30)
	public String nombreRepresentante;

	@Column(length = 30)
	public String primerApellidoRepresentante;

	@Column(length = 30)
	public String segundoApellidoRepresentante;

	@Column(length = 80)
	public String razonSocialRepresentante;

	@Column(length = 4)
	public String cdPaisRepresentante;

	@Column(length = 2)
	public String cdProvinciaRepresentante;

	@Column(length = 5)
	public String cdMunicipioRepresentante;

	@Column(length = 160)
	public String direccionRepresentante;

	@Column(length = 5)
	public String codigoPostalRepresentante;

	@Column(length = 160)
	public String mailRepresentante;

	@Column(length = 20)
	public String telefonoRepresentante;

	@Column(length = 100)
	public Enum canalNotificacionRepresentante;

	@Column(length = 160)
	public String direccionElectronicaRepresentante;

	@Column(length = 160)
	public String observaciones;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "apunteRegistro")
	public ApunteRegistroType apunteRegistroType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Enum getTipoIdentificadorInteresado() {
		return tipoIdentificadorInteresado;
	}

	public void setTipoIdentificadorInteresado(Enum tipoIdentificadorInteresado) {
		this.tipoIdentificadorInteresado = tipoIdentificadorInteresado;
	}

	public String getIdentificadorInteresado() {
		return identificadorInteresado;
	}

	public void setIdentificadorInteresado(String identificadorInteresado) {
		this.identificadorInteresado = identificadorInteresado;
	}

	public String getNombreInteresado() {
		return nombreInteresado;
	}

	public void setNombreInteresado(String nombreInteresado) {
		this.nombreInteresado = nombreInteresado;
	}

	public String getPrimerApellidoInteresado() {
		return primerApellidoInteresado;
	}

	public void setPrimerApellidoInteresado(String primerApellidoInteresado) {
		this.primerApellidoInteresado = primerApellidoInteresado;
	}

	public String getSegundoApellidoInteresado() {
		return segundoApellidoInteresado;
	}

	public void setSegundoApellidoInteresado(String segundoApellidoInteresado) {
		this.segundoApellidoInteresado = segundoApellidoInteresado;
	}

	public String getRazonSocialInteresado() {
		return razonSocialInteresado;
	}

	public void setRazonSocialInteresado(String razonSocialInteresado) {
		this.razonSocialInteresado = razonSocialInteresado;
	}

	public String getCdPaisInteresado() {
		return cdPaisInteresado;
	}

	public void setCdPaisInteresado(String cdPaisInteresado) {
		this.cdPaisInteresado = cdPaisInteresado;
	}

	public String getCdProvinciaInteresado() {
		return cdProvinciaInteresado;
	}

	public void setCdProvinciaInteresado(String cdProvinciaInteresado) {
		this.cdProvinciaInteresado = cdProvinciaInteresado;
	}

	public String getCdMunicipioInteresado() {
		return cdMunicipioInteresado;
	}

	public void setCdMunicipioInteresado(String cdMunicipioInteresado) {
		this.cdMunicipioInteresado = cdMunicipioInteresado;
	}

	public String getDireccionInteresado() {
		return direccionInteresado;
	}

	public void setDireccionInteresado(String direccionInteresado) {
		this.direccionInteresado = direccionInteresado;
	}

	public String getCodigoPostalInteresado() {
		return codigoPostalInteresado;
	}

	public void setCodigoPostalInteresado(String codigoPostalInteresado) {
		this.codigoPostalInteresado = codigoPostalInteresado;
	}

	public String getMailInteresado() {
		return mailInteresado;
	}

	public void setMailInteresado(String mailInteresado) {
		this.mailInteresado = mailInteresado;
	}

	public String getTelefonoInteresado() {
		return telefonoInteresado;
	}

	public void setTelefonoInteresado(String telefonoInteresado) {
		this.telefonoInteresado = telefonoInteresado;
	}

	public Enum getCanalNotificacionInteresado() {
		return canalNotificacionInteresado;
	}

	public void setCanalNotificacionInteresado(Enum canalNotificacionInteresado) {
		this.canalNotificacionInteresado = canalNotificacionInteresado;
	}

	public String getDireccionElectronicaInteresado() {
		return direccionElectronicaInteresado;
	}

	public void setDireccionElectronicaInteresado(String direccionElectronicaInteresado) {
		this.direccionElectronicaInteresado = direccionElectronicaInteresado;
	}

	public Enum getTipoIdentificadorRepresentante() {
		return tipoIdentificadorRepresentante;
	}

	public void setTipoIdentificadorRepresentante(Enum tipoIdentificadorRepresentante) {
		this.tipoIdentificadorRepresentante = tipoIdentificadorRepresentante;
	}

	public String getIdentificadorRepresentante() {
		return identificadorRepresentante;
	}

	public void setIdentificadorRepresentante(String identificadorRepresentante) {
		this.identificadorRepresentante = identificadorRepresentante;
	}

	public String getNombreRepresentante() {
		return nombreRepresentante;
	}

	public void setNombreRepresentante(String nombreRepresentante) {
		this.nombreRepresentante = nombreRepresentante;
	}

	public String getPrimerApellidoRepresentante() {
		return primerApellidoRepresentante;
	}

	public void setPrimerApellidoRepresentante(String primerApellidoRepresentante) {
		this.primerApellidoRepresentante = primerApellidoRepresentante;
	}

	public String getSegundoApellidoRepresentante() {
		return segundoApellidoRepresentante;
	}

	public void setSegundoApellidoRepresentante(String segundoApellidoRepresentante) {
		this.segundoApellidoRepresentante = segundoApellidoRepresentante;
	}

	public String getRazonSocialRepresentante() {
		return razonSocialRepresentante;
	}

	public void setRazonSocialRepresentante(String razonSocialRepresentante) {
		this.razonSocialRepresentante = razonSocialRepresentante;
	}

	public String getCdPaisRepresentante() {
		return cdPaisRepresentante;
	}

	public void setCdPaisRepresentante(String cdPaisRepresentante) {
		this.cdPaisRepresentante = cdPaisRepresentante;
	}

	public String getCdProvinciaRepresentante() {
		return cdProvinciaRepresentante;
	}

	public void setCdProvinciaRepresentante(String cdProvinciaRepresentante) {
		this.cdProvinciaRepresentante = cdProvinciaRepresentante;
	}

	public String getCdMunicipioRepresentante() {
		return cdMunicipioRepresentante;
	}

	public void setCdMunicipioRepresentante(String cdMunicipioRepresentante) {
		this.cdMunicipioRepresentante = cdMunicipioRepresentante;
	}

	public String getDireccionRepresentante() {
		return direccionRepresentante;
	}

	public void setDireccionRepresentante(String direccionRepresentante) {
		this.direccionRepresentante = direccionRepresentante;
	}

	public String getCodigoPostalRepresentante() {
		return codigoPostalRepresentante;
	}

	public void setCodigoPostalRepresentante(String codigoPostalRepresentante) {
		this.codigoPostalRepresentante = codigoPostalRepresentante;
	}

	public String getMailRepresentante() {
		return mailRepresentante;
	}

	public void setMailRepresentante(String mailRepresentante) {
		this.mailRepresentante = mailRepresentante;
	}

	public String getTelefonoRepresentante() {
		return telefonoRepresentante;
	}

	public void setTelefonoRepresentante(String telefonoRepresentante) {
		this.telefonoRepresentante = telefonoRepresentante;
	}

	public Enum getCanalNotificacionRepresentante() {
		return canalNotificacionRepresentante;
	}

	public void setCanalNotificacionRepresentante(Enum canalNotificacionRepresentante) {
		this.canalNotificacionRepresentante = canalNotificacionRepresentante;
	}

	public String getDireccionElectronicaRepresentante() {
		return direccionElectronicaRepresentante;
	}

	public void setDireccionElectronicaRepresentante(String direccionElectronicaRepresentante) {
		this.direccionElectronicaRepresentante = direccionElectronicaRepresentante;
	}

	public String getObservaciones() {
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
