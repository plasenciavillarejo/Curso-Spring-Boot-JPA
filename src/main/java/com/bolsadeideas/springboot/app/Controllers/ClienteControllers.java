package com.bolsadeideas.springboot.app.Controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.service.IClientService;
import com.bolsadeideas.springboot.app.util.paginator.PageRender;

@Controller
/*
 * Usamos sesiones para guardar el id del cliente cuando se invoca el método
 * crear o editar (método get) Obtenemos el objeto cliente, lo guarda en le
 * sesión y lo pasa a la vista de está forma todos los datos pasados a la vista
 * quedan persistentes hasta que se envíe al método guardar que posteriormente
 * se tiene que eliminar la sesión.
 */
@SessionAttributes("cliente")
public class ClienteControllers {


// Inyectamos de forma directa el clienteService el cual se usa para el método fachada, de forma que no se accede de forma
//	directa a los métodos DAO
	@Autowired
	private IClientService clienteService;

	
	/* ----------------------------------------------------------------------- */
	/* Ver la factura del cliente*/
	
	@GetMapping(value="/ver/{id}")
	public String ver (@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Cliente cliente = clienteService.findOne(id);
		if (cliente==null) {
			flash.addFlashAttribute("Error", "El cliente no existe en la base de datos");
			return "redirect:/listar";
		}
		model.put("cliente", cliente);
		model.put("titulo","Detalle cliente" + cliente.getNombre());
		return "ver";
	}
	
	/* ----------------------------------------------------------------------- */
	
	/* Listar Cliente: 
	  	Queremos obtener el Page la página actual, página '0', '1', '2', etc ...*/

	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public String Listar(@RequestParam(name="page",defaultValue = "0") int page, Model model) {
		
	/* Lo hacemos de la forma estática, Pageable pageRequest  = new ... (Está Deprecated)
	 	size= Cantidad registros que queremos mostrar por cada página. (Indico 4 Registro por página) */	
		Pageable pageRequest = PageRequest.of(page, 5);
		
	/* Llamamos al método Page de la clase ClientesServiceImple.java y le pasamos el valor recogido pageRequest
	 	De modo que obtendemos la lísta paginada de cliente con este método.*/
		Page<Cliente> clientes = clienteService.findall(pageRequest);
	
		
	/* Creamos el PageRender
	 	Sirve para desplazarnos entre páginas.*/	
		PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);
		
		model.addAttribute("titulo", "Listado de clientes");
	/* Pasamos como párametro los cliente obtenidos por cada página.*/
		model.addAttribute("clientes", clientes);
	/* Pasamoa a la vista nuestro PageRender*/
		model.addAttribute("page", pageRender);
		return "listar";
	}
	/* ----------------------------------------------------------------------- */

	/* Crear Cliente: */

	// 1.- Se recibe el objeto cliente,
	@RequestMapping(value = "/form")
	public String crear(Map<String, Object> model) {

		Cliente cliente = new Cliente();

		/* Pasamos los datos a la vista: */
		model.put("cliente", cliente);
		model.put("titulo", "Formulario de Cliente");
		return "form";
	}

	/* Editar Cliente: */

	// 2.- Se recibe el objeto cliente para editarlo.
	@RequestMapping(value = "/form/{id}")
	public String editar(@PathVariable(value = "id") long id, Map<String, Object> model,RedirectAttributes flash) {

		Cliente cliente = null;

		if (id > 0) {
			cliente = clienteService.findOne(id);
			if (cliente == null) {
				flash.addFlashAttribute("error", "El ID del cliente no existe en la BD!");
				return "redirect:/listar";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del cliente no puede ser 0!");
			return "redirect:/listar";
		}

		// Pasamos los datos a la vista.
		model.put("cliente", cliente);
		model.put("titulo", "Editar Cliente");

		return "form";
	}

	// 3.- Una vez que recibimos el cliente lo guardamos.
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model,RedirectAttributes flash ,SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Cliente");
			return "form";
		}
		// Al indicar != null me da error, solución != OL
		String mensajeFlash = (cliente.getId() != 0L)? "Cliente Editado con éxito!" : "Cliente Creado con éxito!";
		clienteService.save(cliente);
		// Cuando invoca este metodo borra la sesion
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:listar";
	}
	/* ----------------------------------------------------------------------- */

	/* Eliminar Cliente. */

	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") long id, RedirectAttributes flash) {

		if (id > 0) {
			clienteService.delete(id);
			flash.addFlashAttribute("success", "Cliente Eliminado con exito!");
		}
		return "redirect:/listar";

	}
}
