package com.bolsadeideas.springboot.app.models.service;

import java.util.List;

import com.bolsadeideas.springboot.app.models.entity.Cliente;



/* Patron de diseño : Facade

La clase Service está basado en el patron fachada, un único punto de acceso hacia distintos daos o repositorios.
Podemos trabajar con diferentes clases daos, ademas podemos evitar tener que acceder de forma directa a los datos 
dentro los controladores 
*/

public interface IClientService {
		
	/* Copiamos los métodos definidos en IClientDao.java*/

	/* Listar todos los clientes. */
	 public List<Cliente> findall();

	 /* Gurdar  y Modificar un cliente. */
	 public void save(Cliente cliente);

	 /* Buscar cliente por su id*/
	 public Cliente findOne(Long id);
	 
	 /* Eliminar CLiete*/
	 public void delete (long id);
	

}
