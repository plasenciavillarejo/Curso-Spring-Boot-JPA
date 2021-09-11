package com.bolsadeideas.springboot.app.Controllers;

import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	/* 1.- Creamos la clase Logger. */
	private final Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
	
	
	/* ----------------------------------------------------------------------- */
	/* 1.- Ver la factura del cliente
	   2.- Se usa al pulsar la vista principal al pulsar el ID [1] -> Te redirige a la vista ver.html*/
	
	@GetMapping(value="/ver/{id}")
	public String ver (@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		
		Cliente cliente = clienteService.findOne(id);
		if (cliente==null) {
			flash.addFlashAttribute("Error", "El cliente no existe en la base de datos");
			return "redirect:/listar";
		}
		log.info("Leyendo clase Listar.");
		
		model.put("cliente", cliente);
		model.put("titulo","Detalle cliente: " + cliente.getNombre());
		return "ver";
	}
		
	/* ----------------------------------------------------------------------- 
	Listar de forma continuada, sin usar page render // @RequestMapping(value="/listar", method = RequestMethod.GET)
	@GetMapping(value = "/listar")
	public String Listar(Model model) {
		model.addAttribute("titulo", "Listado de Clientes");
		model.addAttribute("clientes", clienteService.findall());
		log.info("Leyendo clase Listar.");
		return "listar";}*/	
	/* ----------------------------------------------------------------------- */
	
	
	/* ----------------------------------------------------------------------- */
	/* ----------------------------------------------------------------------- */
	/* Listar Cliente: 
	  	Queremos obtener el Page la página actual, página '0', '1', '2', etc ...*/

	@RequestMapping(value = {"/listar","/"}, method = RequestMethod.GET)
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
	/* ----------------------------------------------------------------------- */
	
	/* Crear Cliente: */

	/* 1.- Creamos un cliente.
	   2.- Cuando accedemos a la vista /form, al crear al cliente, nos redirige a esté metodo del controlador. */
	
	@RequestMapping(value = "/form")
	public String crear(Map<String, Object> model) {
		
		Cliente cliente = new Cliente();
		/* Pasamos los datos a la vista: */
		model.put("cliente", cliente);
		model.put("titulo", "Formulario de Cliente");
		return "form";
	}

	/* 2.- Una vez que tenemos el Cliente en memoría, realizamos un metodo POST para poder guardarlo */
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model,RedirectAttributes flash ,
			SessionStatus status) {

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
	/* ----------------------------------------------------------------------- */
		
	/* 1.- Editamos un cliente.
	   2.- Cuando accedemos a la vista Editar.html, y pulsamos en el boton "Editar Cliente", nos redirigira
	   al método  dentro del controlador "guardareditar()" que captura dicha información de la vista*/
	@RequestMapping(value = "/editar", method = RequestMethod.POST)
	private String guardareditar(@Valid Cliente cliente, Model model, SessionStatus status, BindingResult result) {
		/* Validacion */

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario Cliente");
			return "editar";
		}
		clienteService.save(cliente);
		status.setComplete();
		return "redirect:listar";
	}
	
	/* 2.- Editar un cliente accediendo a la vista creada editar.html */

	@RequestMapping(value = "/editar/{id}")
	private String edit(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Cliente cliente = null;

		if (id > 0) {
			cliente = clienteService.findOne(id);
			if (cliente == null) {
				flash.addFlashAttribute("Error", "El cliente no existe en la BD");
			}
		} else {
			flash.addFlashAttribute("Error", "El ID del cliente no puede ser 0.");
			return "redirect:/listar";
		}

		/* Pasamos los datos a la vista. */
		model.put("cliente", cliente);
		model.put("titulo", "Editar Cliente");

		return "editar";
	}
	
	/* ----------------------------------------------------------------------- */
	/* ----------------------------------------------------------------------- */

	
	/* 5.- Eliminar Cliente. */

	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") long id, RedirectAttributes flash) {

		if (id > 0) {
			clienteService.delete(id);
			flash.addFlashAttribute("success", "Cliente Eliminado con exito!");
		}
		return "redirect:/listar";
	}
}
