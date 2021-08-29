package com.bolsadeideas.springboot.app.models.dao;
//package com.bolsadeideas.springboot.app.models.dao;
//
//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//import org.springframework.stereotype.Repository;
//import com.bolsadeideas.springboot.app.models.entity.Cliente;
//
//@Repository
//public class ClienteDaoImpl implements IClienteDao {
//
//	@PersistenceContext
//	private EntityManager em;
//
//	/* -----------------------------------------------------------------------*/
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Cliente> findall() {
//		return em.createQuery("from Cliente").getResultList();
//	}
//
//	/* -----------------------------------------------------------------------*/	
//
///* Nota:
//  	Este método se indica tanto para guardar como para editar el cliente. */
//	
//	@Override
//	public void save(Cliente cliente) {
//// Al indicar != null me da error, solución != OL
//		if(cliente.getId() != 0L && cliente.getId() > 0) {
//			// El método merge se utiliza para editar si exicte el id en la BD.
//			em.merge(cliente);
//		}else {
//			// Realizamos un persist para crear un nuevo cliente si no existe el id en la BD.
//			em.persist(cliente);
//		}
//	}
//
//	
//	/* -----------------------------------------------------------------------*/
//	@Override
//	public Cliente findOne(Long id) {
//
//	/*Con el entityManager buscamos el cliente, indicando Cliente.class lo busca en la base de datos y nos lo devuelve.*/
//		return em.find(Cliente.class, id);
//	}
//
//	
//	/* -----------------------------------------------------------------------*/
//
//	@Override
//	public void delete(long id) {
//
//		// 1.- Obtenemos el cliente para borrarlo
//		Cliente cliente = findOne(id);
//		
//		// 2.- Borramos el cliente con el id obtenido
//		em.remove(cliente);	
//	}
//}
