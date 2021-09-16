package com.bolsadeideas.springboot.app.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.service.IClientService;


/* 1.- Para convertir nuestra aplicación en una API-REST debemos anotarlo con RESTCONTROLLER
 * 2.- RestController -> Combina el Controller + ResponseBody. Todos sus metodos responde a un Responsebody */

@RestController
@RequestMapping("/api/clientes")
public class ClienteRestController {

	@Autowired
	IClientService clienteService;

	
	/* ----------------------------------------------------------------------- */
	/*                  LISTAR usando REST (Metodo Handler)	                   */
	/* ----------------------------------------------------------------------- */
	
	/* 1.- Vamos a retornar un objeto simple de java "pojo", con getters y setters para desplegar sus atributos en formato JSON o XML-> List<Cliente>
	 * 2.- Anotamos con @ResponseBody cuando usamos @Controller-> El listado cliente se va almacenar en el cuerpo de la respuesta y spring sabra que es un servicio REST (JSON o XML)
	 * 3.- Cuando usamos un @RestController el método ya herede del @ResponseBody por lo que no hace falta incluirlo en el método. */
	
	@GetMapping(value = "/listar")
	public List<Cliente> ListarRest() {
		return clienteService.findall();
	}

	// Crear usuario.
	@PostMapping("/post/{id}")
	public ResponseEntity<Cliente> create (@RequestBody Cliente cliente){
		cliente.setNombre("Jose");
		cliente.setApellido("Plasencia Villarejo");
		cliente.setEmail("plasenciavillarejo@gmail.com");
		
		clienteService.save1(cliente);
		return ResponseEntity.ok(cliente);
	}
	

	
	
}
