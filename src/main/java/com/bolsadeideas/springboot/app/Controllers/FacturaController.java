package com.bolsadeideas.springboot.app.Controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.entity.Factura;
import com.bolsadeideas.springboot.app.models.entity.Producto;
import com.bolsadeideas.springboot.app.models.service.IClientService;

@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController {

	@Autowired
	private IClientService clienteService;

	
	/* ----------------------------------------------------------------------- */
	
	@GetMapping("/form/{clienteId}")
	public String crear(@PathVariable(value = "clienteId") Long clienteId, Map<String, Object> model,
			RedirectAttributes flash) {

		Cliente cliente = clienteService.findOne(clienteId);
		if (cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la base de datos.");

			return "redirect:/listar";
		}

		/* Inyectamos una factura para asiganrsela a un cliente. */
		Factura factura = new Factura();
		factura.setCliente(cliente);

		/* Pasamos la factura a la vista. */

		model.put("factura", factura);
		model.put("titulo", "Crear Factura");
		
		/* Devolvemos la vista en la carpeta "factura/form.html". */
		return "factura/form";
	}

	
	/* ----------------------------------------------------------------------- */
	
	/*
		- Mapping donde se carga la vista = /cargar-productos/{1} donde 1 indica que es el producto 1.
		 	/cargar-productos lo hemos indicado en el templates/factura/js/autocomplete-productos.html , url= /factura/cargar-productos/" + request.term
		- Genera una salida (produces) de aplication/json
		- @PathVariable, recibe una variable String term.
		- @ResponseBody, transforma la salida en json y la guarda dentro del body de la respuesta.
	*/
	
	@GetMapping(value="/cargar-productos/{term}", produces = {"application/json"})
	public @ResponseBody List<Producto> cargarProductos(@PathVariable String term){
		return clienteService.finByNombre(term);
	}
	
	
	
	
}
