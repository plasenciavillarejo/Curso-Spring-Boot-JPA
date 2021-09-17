package com.bolsadeideas.springboot.app.Controllers;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.service.IClientService;


/* 1.- Para convertir nuestra aplicación en una API-REST debemos anotarlo con RESTCONTROLLER
 * 2.- RestController -> Combina el Controller + ResponseBody. Todos sus metodos responde a un Responsebody */

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.DELETE,RequestMethod.POST,RequestMethod.PUT})
public class ClienteRestController {

	@Autowired
	IClientService clienteService;

	
	/* ----------------------------------------------------------------------- */
	/*                  LISTAR usando REST (Metodo Handler)	                   */
	/* ----------------------------------------------------------------------- */
	
	/* 1.- Vamos a retornar un objeto simple de java "pojo", con getters y setters para desplegar sus atributos en formato JSON o XML-> List<Cliente>
	 * 2.- Anotamos con @ResponseBody cuando usamos @Controller-> El listado cliente se va almacenar en el cuerpo de la respuesta y spring sabra que es un servicio REST (JSON o XML)
	 * 3.- Cuando usamos un @RestController el método ya herede del @ResponseBody por lo que no hace falta incluirlo en el método. */
	
	@GetMapping(value = "/listarRest")
	public List<Cliente> ListarRest() {
		return clienteService.findall();
	}

	@GetMapping(value = "/listar/{id}")
	public Cliente ListarUsuario (@PathVariable(value = "id") Long id) {
		return clienteService.findOne(id);
	}

	@GetMapping(value = "/insertar")
	public ResponseEntity<List<Cliente>> insertar() {
		Cliente cliente = new Cliente();

		/* 1.- Insercción de datos de forma manual */
		cliente.setNombre("Jose");
		cliente.setApellido("Plasencia Villarejo");
		cliente.setEmail("plasenciavillarejo@gmail.com");
		//cliente.setCreateAt();
		clienteService.save(cliente);
		
		Cliente cliente1 = new Cliente();
		
		cliente1.setNombre("Mari Lola");
		cliente1.setApellido("Lopez Iglesias");
		cliente1.setEmail("lopeziglesias@gmail.com");
		//cliente.setCreateAt();
		clienteService.save(cliente1); 
				
		List<Cliente> lista = new ArrayList<Cliente>();

		for (int i=0; i <= 0; i ++) {
			lista.add(cliente1);
			lista.add(cliente);
		}
		
		return ResponseEntity.ok(lista);
	}
	
	@PostMapping(value="/actualizar")
	public ResponseEntity<Cliente> actualizarCliente(@RequestBody Cliente cliente){
		Optional<Cliente> optionalCliente= Optional.of(clienteService.findOne(cliente.getId()));
		
		if(optionalCliente.isPresent()) {
			Cliente actualizarcliente = optionalCliente.get();
			clienteService.save(actualizarcliente);
			return ResponseEntity.ok(actualizarcliente);
		}else {
			return ResponseEntity.notFound().build();
		}
		
	}
	
	
	@DeleteMapping(value ="/delete/{id}")
	public ResponseEntity<Void> borrarCliente(@PathVariable(value = "id") Long id){
		clienteService.delete(id);
		return null;		
	}
	
	

	
	
	
	
	
	
}
