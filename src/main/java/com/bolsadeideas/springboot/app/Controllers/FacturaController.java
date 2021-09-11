package com.bolsadeideas.springboot.app.Controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.entity.Factura;
import com.bolsadeideas.springboot.app.models.entity.ItemFactura;
import com.bolsadeideas.springboot.app.models.entity.Producto;
import com.bolsadeideas.springboot.app.models.service.IClientService;


@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController {

	@Autowired
	private IClientService clienteService;

	private final Logger log = org.slf4j.LoggerFactory.getLogger(getClass());
	
	/* ----------------------------------------------------------------------- */
	
	
	
	@GetMapping("/ver/{id}")
	public String verDetalleFactura(@PathVariable(value="id") Long id, 
			Model model, 
			RedirectAttributes flash) {

		Factura factura = clienteService.findFacturaById(id);
		
		if (factura == null) {
			flash.addAttribute("Error", "NO hay factura para el cliente");
			return "redirect:/listar";
		}
		
		model.addAttribute("factura", factura);
		model.addAttribute("titulo", "Factura: ".concat(factura.getDescripcion()));
		
		
		return "factura/ver";
	}
	
	
	
	
	
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
	
	
	/* 1.- Guardamos la factura. 
	   2.- Vamos a recoger los 2 parámertros que se indica en plantilla-items-html
	   		-> name="item_id[]" y  name="cantidad[]" y */
	
	@PostMapping("/form")
	/* Tenemos que añadir los nombres de los inputs de plantilla-items.xhtml para pasarlos por @Requestparam
	Para validar los campos en el messager.propertis debemos añadir el @Valid. que valida de forma automatica,
	también necesitaos el BindingResult que permite comprobar si hubieron errores en la factura.
	Importamos el MOdel para pasar datos a la vista*/
	public String guardar(@Valid Factura factura,
			BindingResult result,
			Model model,
			@RequestParam(name="item_id[]", required = false ) Long[] itemId,
			@RequestParam(name="cantidad[]", required = false ) Integer[] cantidad,
			RedirectAttributes flashmessage,
			SessionStatus status) {
		
		// Preguntamos si hay errores.
		if(result.hasErrors()) {
			// Si hay errores, damos un titulo a la factura y retornamos al formulario.
			model.addAttribute("titulo", "Crear Factura");
			return "factura/form";
		}
		
		if(itemId == null || itemId.length == 0) {
			model.addAttribute("titulo", "Crear Factura");
			model.addAttribute("error","Error: La factura no puede no tener Líneas.");
			return "factura/form";
		}
		
		for(int i= 0; i< itemId.length; i++) {
			Producto producto = clienteService.findProductoById(itemId[i]);
			
			ItemFactura linea = new ItemFactura();
			linea.setCantidad(cantidad[i]);
			linea.setProducto(producto);
			factura.addItemFactura(linea);
			// Mostramos por consola el valor del id y de la cantidad con el logger.
			log.info("El ID es : " + itemId[i].toString() + ", y la cantidad es :"+ cantidad[i].toString());
		}
		
		// guardamos la factura en la base de datos
		clienteService.saveFactura(factura);
		// Para finalizar el metod debemos cerrar la sesion con SessionStatus
		status.setComplete();
		flashmessage.addFlashAttribute("Success", "Facturada creada con exito");
		return "redirect:/ver/" + factura.getCliente().getId();
		
		/* Por ultimo tenemos que eliminar el Tbody de plantilla-items por que no se tiene que enviar de modo
		que debemos irnos al autocomplete 
		*/
	}
	
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value="id") Long id,
			RedirectAttributes flashmessenger) {
		
		// Obtengo la factura.
		Factura factura = clienteService.findFacturaById(id);
		
		if(factura != null) {
			clienteService.EliminarFactura(id);
			flashmessenger.addFlashAttribute("success","Factura eliminada con exito");
			return "redirect:/ver/"+factura.getCliente().getId();
		}
		flashmessenger.addFlashAttribute("error","La facutra no existe en la Base de datos, no se puede eliminar!");
		return "return:/listar";
	}	
}
