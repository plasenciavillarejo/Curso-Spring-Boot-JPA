package com.bolsadeideas.springboot.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.entity.Factura;

public interface IFacturaDao extends CrudRepository<Factura, Long>{

	/* 1.- Mejoramos la consulta de Factura.
	 * 2.- Obtenemos la Factura sin necesidad de realizar la carga perezosa(LAZY),si no que viene todo de una vez (Evitamos realizar 7 consultas seguidas). */
	
	@Query("select f from Factura f join fetch f.cliente c join fetch f.items l join fetch l.producto where f.id=?1")
	public Factura fetchByIWithClienteWithItemFacturaWithProducto(Long id);
	
	@Query(value ="select * from facturas where descripcion= :descripcion",
			nativeQuery = true)
	List<Factura> findByIdFactura(@Param("descripcion") String descripcion) throws Exception;

}
