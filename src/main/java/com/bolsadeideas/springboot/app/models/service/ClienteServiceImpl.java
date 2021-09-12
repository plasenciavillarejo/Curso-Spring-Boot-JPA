package com.bolsadeideas.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.springboot.app.models.dao.IClienteDao;
import com.bolsadeideas.springboot.app.models.dao.IFacturaDao;
import com.bolsadeideas.springboot.app.models.dao.IProductoDao;
import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.entity.Factura;
import com.bolsadeideas.springboot.app.models.entity.Producto;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Service
public class ClienteServiceImpl implements IClientService {

	/*
	 * Podemos indicar todos los daos que tengamos para acceder a ellos mediante el
	 * método fachada "IClientService" De modo que si tenemos diferentes daos
	 * podemos inyectarlos aquí y acceder de forma individual a cada uno de ellos
	 */
	@Autowired
	private IClienteDao clienteDao;

	@Autowired
	private IProductoDao productoDao;

	@Autowired
	private IFacturaDao facturaDao;

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findall() {
		return (List<Cliente>) clienteDao.findAll();
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
		clienteDao.save(cliente);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findOne(Long id) {
		return clienteDao.findById(id).orElse(null);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Cliente fetchByIdWithFacturas(Long id) {
		return clienteDao.fetchByIdWithFacturas(id);
	}

	@Override
	@Transactional
	public void delete(long id) {
		clienteDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> findall(Pageable pageable) {
		return clienteDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Producto> finByNombre(String term) {
		return productoDao.findByNombre(term);
		/*
		 * 2º Forma de buscarlo. return productoDao.findByNombreLikeIgnoresCase(term);
		 */
	}
	

	
	 /* ************************************************************************* */
						/* BUSCAR CLIENTES MEDIANTE POSTMAN.*/
	/* ************************************************************************* */
	
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findByLastnameAndFirstname(String nombre, String apellido) {
			return clienteDao.findByLastnameAndFirstname(nombre, apellido);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findByNombreAndApellido(String filtro) throws Exception {
		try {
			List<Cliente> cliente =clienteDao.findByNombreAndApellido(filtro); 
			return 	cliente;
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
		
	/* ************************************************************************* */ 
							/* MÉTODOS PARA LA FACTURA. */
	/* ************************************************************************* */		
	
	@Override
	@Transactional
	public void saveFactura(Factura factura) {
		facturaDao.save(factura);
	}

	@Override
	@Transactional(readOnly = true)
	public Producto findProductoById(Long id) {
		return productoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public Factura findFacturaById(Long id) {
		return facturaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void EliminarFactura(Long id) {
		facturaDao.deleteById(id);
	}

	@Override
	public Factura fetchByIWithClienteWithItemFacturaWithProducto(Long id) {
		return facturaDao.fetchByIWithClienteWithItemFacturaWithProducto(id);
	}
	
}
