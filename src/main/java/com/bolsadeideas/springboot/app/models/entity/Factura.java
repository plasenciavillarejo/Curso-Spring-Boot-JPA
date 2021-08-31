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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ManyToAny;

/* Marcamos a la clase de Persistencia.*/
@Entity
@Table(name="facturas")
public class Factura implements Serializable {

	@Id
/* Lo indicamos que es auto-incremental*/
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String descripcion;
	private String observacion;
	
/* Indicamos el formato en el cual se va a guardar en la tabla. Solo queremos la fecha (Date)*/
	@Temporal(TemporalType.DATE)
/* Indicamos el nombre del campo en la tabla*/
	@Column(name = "create_at")
	private Date createAt;
	
/* Se encarga de generar la fcha. */
	@PrePersist
	public void prePresist() {
		createAt = new Date();
	}
	
/* Esto sería para tener una relación con el cliente
 	Muchas facturas un cliente = ManyToOne 
 	Indicamos el Fetch = Carga Perezosa (Forma más recomendada) solo realiza la consulta cuando se le llama.
 	Eager = Trae todo de una vez, puede cargar demasiado la base de datos. */
	@ManyToOne(fetch = FetchType.LAZY)
	private Cliente cliente;

/* Una factura, muchos itemFactura. */
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
/* La relación es de un solo sentido, debemos indicar cual es la llave foranea que va ha realacionar factura con ItemFactura. 
 	Tendremos un campo llamado factura_id en la tabla facturas_item*/
	@JoinColumn(name = "factura_id")
	private List<ItemFactura> items;
	
/* Constructor. */	

	public Factura() {
		this.items = new ArrayList<ItemFactura>();
	}	
	
/* Metodo para agregar ItemFacturas. */	
	public void addItemFactura(ItemFactura item) {
		this.items.add(item);
	}
	
/* Método para calcular el total de Facturas. */
	
	public Double getTotal() {
		Double total = 0.0;
		int size = items.size();
	
		for(int i =0; i<size; i ++) {
			total += items.get(i).calcularImporte();
		}
	return total;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	private static final long serialVersionUID = 1L;

	public List<ItemFactura> getItems() {
		return items;
	}

	public void setItems(List<ItemFactura> items) {
		this.items = items;
	}
	

	
	
}
