package com.bolsadeideas.springboot.app.Controllers;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Past;

import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.service.IClientService;
import com.bolsadeideas.springboot.app.util.paginator.PageRender;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

	protected final Log logger = LogFactory.getLog(this.getClass());

// Inyectamos de forma directa el clienteService el cual se usa para el método fachada, de forma que no se accede de forma
//	directa a los métodos DAO
	@Autowired
	private IClientService clienteService;

	/* 1.- Creamos la clase Logger. */
	private final Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
	
	
	/* ----------------------------------------------------------------------- */
	/*	  				 	  LISTAR FACTURA CLIENTE				    	   */
	/* ----------------------------------------------------------------------- */
	
	
	/* 1.- Ver la factura del cliente
	   2.- Se usa al pulsar la vista principal al pulsar el ID [1] -> Te redirige a la vista ver.html*/
	
	@GetMapping(value="/ver/{id}")
	public String ver (@PathVariable(value="id") Long id, 
			Map<String, Object> model, RedirectAttributes flash) {
		
		/*Cliente cliente = clienteService.findOne(id);*/
		
		Cliente cliente = clienteService.fetchByIdWithFacturas(id);
		if (cliente==null) {
			flash.addFlashAttribute("Error", "El cliente no existe en la base de datos");
			return "redirect:/listar";
		}
		log.info("Leyendo clase Listar: usuario -> " + cliente);
		
		model.put("cliente", cliente);
		model.put("titulo","Detalle cliente:  " + cliente.getNombre() + " " + cliente.getApellido());
		return "ver";
	}
		

	/* ----------------------------------------------------------------------- */
	/*							     LISTAR TODO							   */
	/* ----------------------------------------------------------------------- */
	
	/*
	Listar de forma continuada, sin usar page render // @RequestMapping(value="/listar", method = RequestMethod.GET)
	@GetMapping(value = "/listar")
	public String Listar(Model model) {
		model.addAttribute("titulo", "Listado de Clientes");
		model.addAttribute("clientes", clienteService.findall());
		log.info("Leyendo clase Listar.");
		return "listar";}*/	
	
	
	
	/* ----------------------------------------------------------------------- */
	/*									LISTAR								   */
	/* ----------------------------------------------------------------------- */
	/* Listar Cliente: 
	  	Queremos obtener el Page la página actual, página '0', '1', '2', etc ...*/

	@RequestMapping(value = {"/listar","/"}, method = RequestMethod.GET)
	public String Listar(@RequestParam(name="page",defaultValue = "0") int page, Model model,
			Authentication authentication,
			HttpServletRequest request) {
		
	/* Diferentes forma de obtener el ROL del usuario logueado.*/	
	
		/* 1.- Método -> Vamos a implementar el usuario authenticado en el controlador. 
	   	Nos mostrara el usuario por consola una vez que nos registremos. */	
		if(authentication != null ) {
			logger.info("Hola el usuario autenticado es: ".concat(authentication.getName()));
		}	
		
		
		/* 2.- Metodo -> Para implementar la autenticación de forma estática lo podemos hacer de la siguiente manera. */	
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (auth != null) {
			logger.info("Utilizando forma estática SecurityContextHolder.getContext().getAuthentication(): Usuario Autenticado: "+ auth.getName().concat(" tienes acceso!"));
		}

		/* 3.- Metodo -> Llamamos a la clase creada -> Public boolean hasRole(String role) <- y validamos.*/	
		
		if(hasRole("ROLE_ADMIN")) {
			logger.info("Utilizando la clase creada Public boolean hasRole(String role): Usuario Autenticado: ".concat(auth.getName()).concat(" tienes acceso!"));
		}else {
			logger.info("Hola ".concat(auth.getName()).concat(" NO tienes acceso!"));
		}
		
		/* 4.- Metodo -> Usando clase integrada en spring. */
		SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(request,"ROLE_");
		
		if(securityContext.isUserInRole("ADMIN")) {
			logger.info("Utilizando forma SecurityContextHolderAwareRequestWrapper: Usuario Autenticado: "+ auth.getName().concat(" tienes acceso!"));
		}else {
			logger.info("Utilizando forma SecurityContextHolderAwareRequestWrapper: Usuario Autenticado: "+ auth.getName().concat(" No tienes acceso!"));
		}
		
		/* 5.- Metodo -> Usando HttpServletRequest */
		if(request.isUserInRole("ROLE_ADMIN")) {
			logger.info("Utilizando forma HttpServletRequest: Usuario Autenticado: "+ auth.getName().concat(" tienes acceso!"));
		}else {
			logger.info("Utilizando forma HttpServletRequest: Usuario Autenticado: "+ auth.getName().concat(" No tienes acceso!"));
		}
		
		
	/* 1.- Lo hacemos de la forma estática, Pageable pageRequest  = new ... (Está Deprecated)
	 		size= Cantidad registros que queremos mostrar por cada página. (Indico 4 Registro por página) */	
		Pageable pageRequest = PageRequest.of(page, 5);
		
	/* 2.- Llamamos al método Page de la clase ClientesServiceImple.java y le pasamos el valor recogido pageRequest
	 	De modo que obtendemos la lísta paginada de cliente con este método.*/
		Page<Cliente> clientes = clienteService.findall(pageRequest);
		
	/* 3.- Creamos el PageRender
	 		Sirve para desplazarnos entre páginas.*/	
		PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);
		
		model.addAttribute("titulo", "Listado de clientes");
	/* 4.- Pasamos como párametro los cliente obtenidos por cada página.*/
		model.addAttribute("clientes", clientes);
	/* 5.- Pasamoa a la vista nuestro PageRender*/
		model.addAttribute("page", pageRender);
		return "listar";
	}
	
	/* ----------------------------------------------------------------------- */
	/*									CREAR								   */
	/* ----------------------------------------------------------------------- */
	
	/* Crear Cliente: */

	/* 1.- Creamos un cliente.
	   2.- Cuando accedemos a la vista /form, al crear al cliente, nos redirige a esté metodo del controlador. */
	
	@RequestMapping(value = "/form")
	public String crear(Map<String, Object> model) {
		
		Cliente cliente = new Cliente();
		/* Pasamos los datos a la vista: */
		model.put("titulo", "Formulario de Cliente");
		model.put("cliente", cliente);
		
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
		String mensajeFlash = (cliente.getId() != 0L)? "Cliente Creado con éxito!" : "No se ha podido crear el Cliente.!";
		clienteService.save(cliente);
		// Cuando invoca este metodo borra la sesion
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:listar";
	}
	
	
	/* ----------------------------------------------------------------------- */
	/*									EDITAR								   */
	/* ----------------------------------------------------------------------- */
		
	/* 1.- Editamos un cliente.
	   2.- Cuando accedemos a la vista Editar.html, y pulsamos en el boton "Editar Cliente", nos redirigira
	   al método  dentro del controlador "guardareditar()" que captura dicha información de la vista*/
	
	
	/* 1.- Accedemos a la vista y la mostramos junto a los datos de el cliente, no realizamos ninguna acción.*/
	
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
	
	/* 2.- Al Pulsar "Aceptar" nos redirigira a este metodo que realizar una petición a la Base de datos y actualizá el registro.*/
	
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
	
	
	/* ----------------------------------------------------------------------- */
	/*									ELIMINAR							   */
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
		
	/* ----------------------------------------------------------------------- */
	/* ----------------------------------------------------------------------- */
	
	/* Buscar un cliente Nombre y Apellido-> Explicación de como funciona. 
	  --------------------------------------------------------------------
	   1.- En el listar.html nos redirección al método -> @RequestMapping(value = "/buscar") y este nos devuelve la vista busqueda.html
	   2.- Posteriormente dentro de la vista buscamos nombre y apellido y al pulsar el boton este ira a buscar el método th:action="@{/buscarC}" dentro del controlador.
	   2.- @RequestMapping(value = "/buscarC", method = RequestMethod.GET) -> Obtiene la acción de la vista buscar.html y realiza la acción de buscar. Nos devuelve el usuairo. 
	   
	@RequestMapping(value = "/buscar")
	public String buscarcliente() throws Exception{

		return "buscar";
	}
  	
	@RequestMapping(value = "/buscarC", method = RequestMethod.GET)
	public String buscarC(@Param(value = "nombre") String nombre, 
			   				    @Param(value = "apellido") String apellido, Model model) throws Exception{
		try {
			model.addAttribute("titulo", "Buscar cliente");
			model.addAttribute("clientes", clienteService.findByLastnameAndFirstname(nombre, apellido));
			
			log.info("Dentro de la clase Buscar Un Cliente por Nombre y Apellido");
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
		return "buscar";
	}
	
	*/

	

	/* ----------------------------------------------------------------------- */
	/*						BUSCAR UN USUARIO CONCRETO  					   */
	/* ----------------------------------------------------------------------- */
	
	
	/* 6.- Busqueda correcta de un usuario por Nombre y Apellido. */
		
	@RequestMapping(value = "/buscar", method = RequestMethod.GET)
	public String buscarC(@Param(value = "nombre") String nombre, 
			   			  @Param(value = "apellido") String apellido, Model model) throws Exception{
		try {
			model.addAttribute("titulo", "Buscar cliente");
			model.addAttribute("clientes", clienteService.findByLastnameAndFirstname(nombre, apellido));
			
			log.info("Dentro de la clase Buscar Un Cliente por Nombre y Apellido");
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
		return "buscar";
	}
	
	/* ----------------------------------------------------------------------- */
	/*	  Validación del Role al iniciar sesión den la clase Controller.	   */
	/* ----------------------------------------------------------------------- */
	
	/* 7.- Validación de Rol del usuario.*/
	
	private boolean hasRole(String role) {
		SecurityContext  context=SecurityContextHolder.getContext();
		
		if (context == null) {
			return false;
		}
		Authentication authentication = context.getAuthentication();
		if(authentication == null) {
			return false;
		}
		
		/* 1.- Obtenemos validaciones de Roles(Authority)
		   2.- Definimos una Collection que contendra <Cualquier role o que represente un rol en nuestra aplicaición que herede de ella> se define con "?" GrantedAuthority*/
		
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		
		/* 3.- Ahora iremos recorriendo el tipo de ROL e iremos validando que rol tiene, si lo encuentra retornara un true */ 
		
	/*   for(GrantedAuthority authority: authorities) {
		 4.- Validamos que tiene el ROLE, de modo que tiene permisos, por tanto nos devolvera un true. 	
			if(role.equals(authority.getAuthority())) {
				logger.info("Hola ".concat(authentication.getName()).concat(" tu role es:").concat(authority.getAuthority()));
				return true;
			}			
		}*/
		
		/* 5.- Podemos también hacerlo sin necesidad de recorrer un  bucle.
		   El método contains(GrantedAuthority) retorna un booleano, true o false, si contiene o no el elemento en la colección.*/	
	return authorities.contains(new SimpleGrantedAuthority(role));
	}
	
	/* ----------------------------------------------------------------------- */
	/* ----------------------------------------------------------------------- */	
	
	
	@RequestMapping(value="/prueba")
	public String prueba() {
		return "prueba";
	}
	
	
	/* ----------------------------------------------------------------------- */
	/*					MÉTODOS PARA USAR POSTMAN.							   */
	/* ----------------------------------------------------------------------- */
	
	@RequestMapping(value = "/buscarCli")
	public ResponseEntity<?> buscPostman(@Param(value = "nombre") String nombre, 
										   @Param(value = "apellido") String apellido) throws Exception{
		try {
			return ResponseEntity.status(HttpStatus.OK).body(clienteService.findByLastnameAndFirstname(nombre, apellido));
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
		}
	}
	
	@GetMapping("/buscarfil")
	public ResponseEntity<?> findByNombreAndApellido(@RequestParam String filtro) throws Exception {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(clienteService.findByNombreAndApellido(filtro));
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
		}		
	}
	
	@RequestMapping(value = "/eliminarC/{id}")
	public BodyBuilder eliminarC(@PathVariable(value = "id") Long id) {

	      Map<String, Object> paramMap = new HashMap<>();
	        paramMap.put("id", id);
		
		return ResponseEntity.status(HttpStatus.OK);
	}
	
}

