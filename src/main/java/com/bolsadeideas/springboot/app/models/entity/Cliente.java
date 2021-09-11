package com.bolsadeideas.springboot.app.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private long id;

	@NotEmpty
	private String nombre;

	@NotEmpty
	private String apellido;

	@NotEmpty
	@Email
	private String email;

	@NotNull
	@Column(name = "create_At")
	@Temporal(TemporalType.DATE)

	/*
	  Vamos a modicar el campo createAt para que acepte cualquier tipo de fecha y
	 * no de error.
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	/* De está forma acepta la fecha con "-" y con "/" */
	private Date createAt;

	/*
	 * Creamos un método persist - Antes de que ser guarde en la BD. Se va a
	 * ejecutar como un evento (PrePersist) de forma automatica se va a llamar a
	 * este método antes de insertar el registro en la BD. (Se inserta
	 * automaticamente la fecha en la cual se registra el cliente)
	 */
//	@PrePersist
//	public void prePersist() {
//		createAt = new Date();
//	}

	/* Un cliente puede tener muchas facturas, usamos el método list 
	  Un cliente muchas facturas = OneToMany
	  Aquí es más recomenado el Lazy ya que traera una a una la factura del cliente, con EAGER traera todas las facturas de un mismo cliente a la misma vez
	  puede sobrecargar la BD.
	  CascadeType.All = Se reliza todas las consultas en cadena. 
	  MappedBy = Mapeamos el objeto de la clase Factura, "cliente". De esta forma hacermos que sea vireccional.*/
	@OneToMany(mappedBy = "cliente",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Factura> facturas;

	/* Generamos un SuperConstructors */
	public Cliente() {
		facturas = new ArrayList<Factura>();
	}

	/* Creamos el método de agregar factura que sería para agregar factura a factura
	  en la clase cliente. 	 */
	public void addFactura(Factura factura) {
		facturas.add(factura);
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<Factura> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}

	@Override
	public String toString() {
		return nombre + " " +apellido;
	}

	
	
}
