package com.bolsadeideas.springboot.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.bolsadeideas.springboot.app.models.entity.Producto;

public interface IProductoDao extends CrudRepository<Producto, Long>{

	/*@Query es de JPA, se puede realizar la consulta desde la etiqueta.
	 	Se realiza la consulta a nivel de objeto no a nivel de tabla. */
	@Query("select p from Producto p where nombre like %?1%")
	public List<Producto> findByNombre(String term);
	
	/*	2º Forma de buscarlo.
	 	 No estoy usando el método ya que en el ClienteSErvice devuelve el producto.finByNombre(String term)*/
	
	//public List<Producto> findByNombreLikeIgnoresCase(String term);
	
}
