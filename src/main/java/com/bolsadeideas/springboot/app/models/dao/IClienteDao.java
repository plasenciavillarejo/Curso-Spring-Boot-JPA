package com.bolsadeideas.springboot.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.bolsadeideas.springboot.app.models.entity.Cliente;

/* Indicamos en el CrudRepository[T,objt]
	T = Cliente - Clase Entity.
	ob= Llave primaria de la clase "Cliente". Long 
	*/
public interface IClienteDao extends CrudRepository<Cliente, Long> {

	/* Comentamos todos los métodos por que implementamos el CrudRepository 
		 public List<Cliente> findall();
 		 public void save(Cliente cliente);
		 public Cliente findOne(Long id);
		 public void delete (long id);
		 */
}
