package com.bolsadeideas.springboot.app.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.bolsadeideas.springboot.app.models.entity.Cliente;

/* Indicamos en el CrudRepository[T,objt]
	T = Cliente - Clase Entity.
	ob= Llave primaria de la clase "Cliente". Long 
	*/
/* public interface IClienteDao extends CrudRepository<Cliente, Long> {
	Vamos añadir la parte del paginado de modo que debemos sustituir el método CrudRepository por el PaginAndStorign ya que hereda de CrudRepository y es lo mismo.
*/

/* PagingAndSortingRepository Hereda de CrudRepository y además tendremos la paginación */
public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long> {
	
	/* Comentamos todos los métodos por que implementamos el CrudRepository 
		 public List<Cliente> findall();
 		 public void save(Cliente cliente);
		 public Cliente findOne(Long id);
		 public void delete (long id);
		 */
}
