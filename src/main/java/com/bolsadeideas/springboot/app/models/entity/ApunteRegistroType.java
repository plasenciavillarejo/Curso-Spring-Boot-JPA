package com.bolsadeideas.springboot.app.models.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "Apunte_Registro_Type")
public class ApunteRegistroType implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	@NotEmpty
	@Column(length = 20)
	public String nuRegistro;

	@NotEmpty
	public String timestampPresentado;

	@NotEmpty
	public String timestampRegistrado;

	@Column(length = 100)
	public String justificanteFirmado;

	public Long tamanioJustificanteFirmado;

	@Column(length = 20)
	public String hashJustificanteFirmado;

	@Column(length = 30)
	public String tipoMimeJustificanteFirmado;

	@Column(length = 100)
	public String justificanteCSV;

	public Long tamanioJustificanteCVS;

	@Column(length = 20)
	public String hashJustificanteCVS;

	@Column(length = 30)
	public String tipoMimeJustificanteCVS;

	public Boolean tieneFirmaJustificanteCSV;

	@NotEmpty
	@Column(length = 100)
	public String csv;

	@NotEmpty
	@Column(length = 21)
	public String cdAmbitoCreacion;

	@NotEmpty
	@Column(length = 80)
	public String ambitoCreacion;

	@NotEmpty
	@Column(length = 21)
	public String cdAmbitoActual;

	@NotEmpty
	@Column(length = 80)
	public String ambitoActual;

	@SuppressWarnings("rawtypes")
	@NotEmpty
	@Column(length = 20)
	public Enum tipoAsiento;

	@SuppressWarnings("rawtypes")
	@NotEmpty
	@Column(length = 100)
	public Enum estado;

	@Column(length = 21)
	public String cdOrganoOrigen;

	@Column(length = 80)
	public String organoOrigen;

	@Column(length = 21)
	public String cdOrganoDestino;

	@Column(length = 80)
	public String organoDestino;

	@OneToMany(mappedBy = "apunteRegistroType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<InteresadoType> interesadosType;

	@OneToMany(mappedBy = "apunteRegistroType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<AnexoType> anexoType;

	@OneToMany(mappedBy = "apunteRegistroType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<ControlPeticionDiaria> controlPeticionDiaria;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public FormularioType formularioType;

	@OneToOne(mappedBy="idApunteRegistro", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public AvisoRegistro avisoRegistro;

	@NotEmpty
	@Column(length = 240)
	public String resumen;

	@NotEmpty
	@Column(length = 32)
	public String cdAsunto;

	@Column(length = 50)
	public String deAsunto;

	@Column(length = 16)
	public String cdSIA;

	@Column(length = 50)
	public String deCdSIA;

	@Column(length = 16)
	public String cdFormulario;

	@Column(length = 50)
	public String deFormulario;

	@Column(length = 16)
	public String referenciaExterna;

	@Column(length = 80)
	public String nuExpediente;

	@SuppressWarnings("rawtypes")
	@Column(length = 100)
	public Enum tipoTransporte;

	@Column(length = 20)
	public String nuTransporte;

	@Column(length = 80)
	public String nombreUsuario;

	@Column(length = 160)
	public String contactoUsuario;

	@SuppressWarnings("rawtypes")
	@NotEmpty
	@Column(length = 100)
	public Enum documentacionFisica;

	@Column(length = 50)
	public String observaciones;

	@Column(length = 4000)
	public String expone;

	@Column(length = 4000)
	public String solicita;

	@Column(length = 50)
	public String asuntoInterno;

	@Column(length = 20)
	public String codigoCadenaAsientos;

	@NotEmpty
	@Column(length = 14)
	public String timestampFactura;

	@Column(length = 20)
	public String nuRegistroInterno;

	@Column(length = 20)
	public String nuRegistroOrigen;

	public BigDecimal importeFactura;

	@Column(length = 20)
	public String numeroFactura;

	@Column(length = 50)
	public String justificanteUnidadTramitacion;

	public Long cdTipodocumento;

	@Column(length = 100)
	public String deTipodocumento;

	@Column(length = 20)
	public String cdZonaHorariaCreacion;

	@Column(length = 20)
	public String deZonaHorariaCreacion;

	@Column(length = 20)
	public String cdZonaHorariaUsuario;

	@Column(length = 20)
	public String deZonaHorariaUsuario;

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

	public String getTimestampPresentado() {
		return timestampPresentado;
	}

	public void setTimestampPresentado(String timestampPresentado) {
		this.timestampPresentado = timestampPresentado;
	}

	public String getTimestampRegistrado() {
		return timestampRegistrado;
	}

	public void setTimestampRegistrado(String timestampRegistrado) {
		this.timestampRegistrado = timestampRegistrado;
	}

	public List<ControlPeticionDiaria> getControlPeticionDiaria() {
		return controlPeticionDiaria;
	}

	public void setControlPeticionDiaria(List<ControlPeticionDiaria> controlPeticionDiaria) {
		this.controlPeticionDiaria = controlPeticionDiaria;
	}

	public FormularioType getFormularioType() {
		return formularioType;
	}

	public void setFormularioType(FormularioType formularioType) {
		this.formularioType = formularioType;
	}

	public String getJustificanteFirmado() {
		return justificanteFirmado;
	}

	public void setJustificanteFirmado(String justificanteFirmado) {
		this.justificanteFirmado = justificanteFirmado;
	}

	public Long getTamanioJustificanteFirmado() {
		return tamanioJustificanteFirmado;
	}

	public void setTamanioJustificanteFirmado(Long tamanioJustificanteFirmado) {
		this.tamanioJustificanteFirmado = tamanioJustificanteFirmado;
	}

	public String getHashJustificanteFirmado() {
		return hashJustificanteFirmado;
	}

	public void setHashJustificanteFirmado(String hashJustificanteFirmado) {
		this.hashJustificanteFirmado = hashJustificanteFirmado;
	}

	public String getTipoMimeJustificanteFirmado() {
		return tipoMimeJustificanteFirmado;
	}

	public void setTipoMimeJustificanteFirmado(String tipoMimeJustificanteFirmado) {
		this.tipoMimeJustificanteFirmado = tipoMimeJustificanteFirmado;
	}

	public String getJustificanteCSV() {
		return justificanteCSV;
	}

	public void setJustificanteCSV(String justificanteCSV) {
		this.justificanteCSV = justificanteCSV;
	}

	public Long getTamanioJustificanteCVS() {
		return tamanioJustificanteCVS;
	}

	public void setTamanioJustificanteCVS(Long tamanioJustificanteCVS) {
		this.tamanioJustificanteCVS = tamanioJustificanteCVS;
	}

	public String getHashJustificanteCVS() {
		return hashJustificanteCVS;
	}

	public void setHashJustificanteCVS(String hashJustificanteCVS) {
		this.hashJustificanteCVS = hashJustificanteCVS;
	}

	public String getTipoMimeJustificanteCVS() {
		return tipoMimeJustificanteCVS;
	}

	public void setTipoMimeJustificanteCVS(String tipoMimeJustificanteCVS) {
		this.tipoMimeJustificanteCVS = tipoMimeJustificanteCVS;
	}

	public Boolean getTieneFirmaJustificanteCSV() {
		return tieneFirmaJustificanteCSV;
	}

	public void setTieneFirmaJustificanteCSV(Boolean tieneFirmaJustificanteCSV) {
		this.tieneFirmaJustificanteCSV = tieneFirmaJustificanteCSV;
	}

	public String getCsv() {
		return csv;
	}

	public void setCsv(String csv) {
		this.csv = csv;
	}

	public String getCdAmbitoCreacion() {
		return cdAmbitoCreacion;
	}

	public void setCdAmbitoCreacion(String cdAmbitoCreacion) {
		this.cdAmbitoCreacion = cdAmbitoCreacion;
	}

	public String getAmbitoCreacion() {
		return ambitoCreacion;
	}

	public void setAmbitoCreacion(String ambitoCreacion) {
		this.ambitoCreacion = ambitoCreacion;
	}

	public String getCdAmbitoActual() {
		return cdAmbitoActual;
	}

	public void setCdAmbitoActual(String cdAmbitoActual) {
		this.cdAmbitoActual = cdAmbitoActual;
	}

	public String getAmbitoActual() {
		return ambitoActual;
	}

	public void setAmbitoActual(String ambitoActual) {
		this.ambitoActual = ambitoActual;
	}

	@SuppressWarnings("rawtypes")
	public Enum getTipoAsiento() {
		return tipoAsiento;
	}

	@SuppressWarnings("rawtypes")
	public void setTipoAsiento(Enum tipoAsiento) {
		this.tipoAsiento = tipoAsiento;
	}

	@SuppressWarnings("rawtypes")
	public Enum getEstado() {
		return estado;
	}

	@SuppressWarnings("rawtypes")
	public void setEstado(Enum estado) {
		this.estado = estado;
	}

	public String getCdOrganoOrigen() {
		return cdOrganoOrigen;
	}

	public void setCdOrganoOrigen(String cdOrganoOrigen) {
		this.cdOrganoOrigen = cdOrganoOrigen;
	}

	public String getOrganoOrigen() {
		return organoOrigen;
	}

	public void setOrganoOrigen(String organoOrigen) {
		this.organoOrigen = organoOrigen;
	}

	public String getCdOrganoDestino() {
		return cdOrganoDestino;
	}

	public void setCdOrganoDestino(String cdOrganoDestino) {
		this.cdOrganoDestino = cdOrganoDestino;
	}

	public String getOrganoDestino() {
		return organoDestino;
	}

	public void setOrganoDestino(String organoDestino) {
		this.organoDestino = organoDestino;
	}

	public List<InteresadoType> getInteresadosType() {
		return interesadosType;
	}

	public void setInteresadosType(List<InteresadoType> interesadosType) {
		this.interesadosType = interesadosType;
	}

	public List<AnexoType> getAnexoType() {
		return anexoType;
	}

	public void setAnexoType(List<AnexoType> anexoType) {
		this.anexoType = anexoType;
	}

	public String getResumen() {
		return resumen;
	}

	public void setResumen(String resumen) {
		this.resumen = resumen;
	}

	public String getCdAsunto() {
		return cdAsunto;
	}

	public void setCdAsunto(String cdAsunto) {
		this.cdAsunto = cdAsunto;
	}

	public String getDeAsunto() {
		return deAsunto;
	}

	public void setDeAsunto(String deAsunto) {
		this.deAsunto = deAsunto;
	}

	public String getCdSIA() {
		return cdSIA;
	}

	public void setCdSIA(String cdSIA) {
		this.cdSIA = cdSIA;
	}

	public String getDeCdSIA() {
		return deCdSIA;
	}

	public void setDeCdSIA(String deCdSIA) {
		this.deCdSIA = deCdSIA;
	}

	public String getCdFormulario() {
		return cdFormulario;
	}

	public void setCdFormulario(String cdFormulario) {
		this.cdFormulario = cdFormulario;
	}

	public String getDeFormulario() {
		return deFormulario;
	}

	public void setDeFormulario(String deFormulario) {
		this.deFormulario = deFormulario;
	}

	public String getReferenciaExterna() {
		return referenciaExterna;
	}

	public void setReferenciaExterna(String referenciaExterna) {
		this.referenciaExterna = referenciaExterna;
	}

	public String getNuExpediente() {
		return nuExpediente;
	}

	public void setNuExpediente(String nuExpediente) {
		this.nuExpediente = nuExpediente;
	}

	@SuppressWarnings("rawtypes")
	public Enum getTipoTransporte() {
		return tipoTransporte;
	}

	@SuppressWarnings("rawtypes")
	public void setTipoTransporte(Enum tipoTransporte) {
		this.tipoTransporte = tipoTransporte;
	}

	public String getNuTransporte() {
		return nuTransporte;
	}

	public void setNuTransporte(String nuTransporte) {
		this.nuTransporte = nuTransporte;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getContactoUsuario() {
		return contactoUsuario;
	}

	public void setContactoUsuario(String contactoUsuario) {
		this.contactoUsuario = contactoUsuario;
	}

	public Enum getDocumentacionFisica() {
		return documentacionFisica;
	}

	public void setDocumentacionFisica(Enum documentacionFisica) {
		this.documentacionFisica = documentacionFisica;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getExpone() {
		return expone;
	}

	public void setExpone(String expone) {
		this.expone = expone;
	}

	public String getSolicita() {
		return solicita;
	}

	public void setSolicita(String solicita) {
		this.solicita = solicita;
	}

	public String getAsuntoInterno() {
		return asuntoInterno;
	}

	public void setAsuntoInterno(String asuntoInterno) {
		this.asuntoInterno = asuntoInterno;
	}

	public String getCodigoCadenaAsientos() {
		return codigoCadenaAsientos;
	}

	public void setCodigoCadenaAsientos(String codigoCadenaAsientos) {
		this.codigoCadenaAsientos = codigoCadenaAsientos;
	}

	public String getTimestampFactura() {
		return timestampFactura;
	}

	public void setTimestampFactura(String timestampFactura) {
		this.timestampFactura = timestampFactura;
	}

	public String getNuRegistroInterno() {
		return nuRegistroInterno;
	}

	public void setNuRegistroInterno(String nuRegistroInterno) {
		this.nuRegistroInterno = nuRegistroInterno;
	}

	public String getNuRegistroOrigen() {
		return nuRegistroOrigen;
	}

	public void setNuRegistroOrigen(String nuRegistroOrigen) {
		this.nuRegistroOrigen = nuRegistroOrigen;
	}

	public BigDecimal getImporteFactura() {
		return importeFactura;
	}

	public void setImporteFactura(BigDecimal importeFactura) {
		this.importeFactura = importeFactura;
	}

	public String getNumeroFactura() {
		return numeroFactura;
	}

	public void setNumeroFactura(String numeroFactura) {
		this.numeroFactura = numeroFactura;
	}

	public String getJustificanteUnidadTramitacion() {
		return justificanteUnidadTramitacion;
	}

	public void setJustificanteUnidadTramitacion(String justificanteUnidadTramitacion) {
		this.justificanteUnidadTramitacion = justificanteUnidadTramitacion;
	}

	public Long getCdTipodocumento() {
		return cdTipodocumento;
	}

	public void setCdTipodocumento(Long cdTipodocumento) {
		this.cdTipodocumento = cdTipodocumento;
	}

	public String getDeTipodocumento() {
		return deTipodocumento;
	}

	public void setDeTipodocumento(String deTipodocumento) {
		this.deTipodocumento = deTipodocumento;
	}

	public String getCdZonaHorariaCreacion() {
		return cdZonaHorariaCreacion;
	}

	public void setCdZonaHorariaCreacion(String cdZonaHorariaCreacion) {
		this.cdZonaHorariaCreacion = cdZonaHorariaCreacion;
	}

	public String getDeZonaHorariaCreacion() {
		return deZonaHorariaCreacion;
	}

	public void setDeZonaHorariaCreacion(String deZonaHorariaCreacion) {
		this.deZonaHorariaCreacion = deZonaHorariaCreacion;
	}

	public String getCdZonaHorariaUsuario() {
		return cdZonaHorariaUsuario;
	}

	public void setCdZonaHorariaUsuario(String cdZonaHorariaUsuario) {
		this.cdZonaHorariaUsuario = cdZonaHorariaUsuario;
	}

	public String getDeZonaHorariaUsuario() {
		return deZonaHorariaUsuario;
	}

	public void setDeZonaHorariaUsuario(String deZonaHorariaUsuario) {
		this.deZonaHorariaUsuario = deZonaHorariaUsuario;
	}

	private static final long serialVersionUID = 1L;

}
