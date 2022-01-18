package com.bolsadeideas.springboot.app.models.dao;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

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

	
	/* 1.- Mejora del buscar una factura del cliente*/
	@Query("select c from Cliente c left join fetch c.facturas f where c.id=?1")
	public Cliente fetchByIdWithFacturas(Long id);
	
	 /* Buscar un cliente por su nombre y apellido usando Jquery. */
	 @Query("select c from #{#entityName} c where c.nombre = :nombre and c.apellido = :apellido")
	 List<Cliente> findByLastnameAndFirstname(@Param("nombre" ) String nombre, 
											 @Param("apellido") String apellido) throws Exception;
	 
	@Query("select c from #{#entityName} c where c.nombre LIKE %:filtro% and c.apellido LIKE %:filtro%")
	 List<Cliente> findByNombreAndApellido(@Param("filtro") String filtro ) throws Exception;
	 
	 
	/* Comentamos todos los métodos por que implementamos el CrudRepository 
		 public List<Cliente> findall();
 		 public void save(Cliente cliente);
		 public Cliente findOne(Long id);
		 public void delete (long id);
		 */

	
	@Query(value = "SELECT * FROM clientes where email = :email and create_at = :createAt",
			nativeQuery = true)
	//@Query("select c from Cliente c where c.create_At = :fecha")
	List<Cliente> findByDate(@Param("email") String email,
							@Param("createAt") String createAt) throws Exception;
	
	
}
