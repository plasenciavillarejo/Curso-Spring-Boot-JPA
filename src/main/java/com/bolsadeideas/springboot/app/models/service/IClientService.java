package com.bolsadeideas.springboot.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.entity.Factura;
import com.bolsadeideas.springboot.app.models.entity.Producto;

/* Patron de diseño : Facade

La clase Service está basado en el patron fachada, un único punto de acceso hacia distintos daos o repositorios.
Podemos trabajar con diferentes clases daos, ademas podemos evitar tener que acceder de forma directa a los datos 
dentro los controladores 
*/

public interface IClientService {
		
	/* Copiamos los métodos definidos en IClientDao.java*/

	public Factura fetchByIWithClienteWithItemFacturaWithProducto(Long id);
	
	/* Listar todos los clientes de forma continua (No se está usando ahora mismo en el ejmplo, sustituido por Page<Cliente>). */
	 public List<Cliente> findall();

	/* 1.- Método de la paginación para un cliente
	   2.- Importamos Pageable = org.springframework.data.domain.Pageable
	   3.- Retornamos un Page */
	 public Page<Cliente> findall(Pageable pageable);
	 
	 /* Guardar un cliente, sirve para cuando:
	    1.- Creamos un Cliente y lo guardamos.
	    2.- Editamos un Cliente y lo guardamos.
	  */
	 public void save(Cliente cliente);

	 /* Buscar cliente por su id*/
	 public Cliente findOne(Long id);
	 
	 
	 /* Mejora de buscar la factura del cliente por su id*/
	 public Cliente fetchByIdWithFacturas(Long id);
	 
	 /* Eliminar CLiete*/
	 public void delete (long id);
		 
	 /* Lista un Producto del cliente. */
	 public List<Producto> finByNombre(String term);
	 
	 /* ************************************************************************* */
	 					/* BUSCAR CLIENTES MEDIANTE POSTMAN.*/
	 /* ************************************************************************* */
	public List<Cliente> findByLastnameAndFirstname(String nombre, String apellido) throws Exception;;
	public List<Cliente> findByNombreAndApellido(String filtro) throws Exception;
	 
	public Cliente save1(Cliente cliente);
	
//	public Cliente findByIdDelete(Long id);
	
	
	/* ************************************************************************* */ 
	 						/* MÉTODOS PARA LA FACTURA. */
	/* ************************************************************************* */	 
	
	 public void saveFactura(Factura factura);
	 
	 public Producto findProductoById(Long id);
	
	 public Factura findFacturaById(Long id);
	 
	 public void EliminarFactura(Long id);
	 

}
