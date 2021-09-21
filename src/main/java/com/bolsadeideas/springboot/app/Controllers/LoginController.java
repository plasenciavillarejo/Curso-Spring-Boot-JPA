package com.bolsadeideas.springboot.app.Controllers;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.entity.Usuario;
import com.bolsadeideas.springboot.app.models.service.IClientService;

@Controller
@SessionAttributes("usuario")
public class LoginController {

	@Autowired
	private IClientService clienteService;
	
	/* 1.- Creamos nuestro LoginControlle para implementar nuestro Login.
	   2.- Va a retornar la vista login.html
	   3.- Se valida mediante el Objeto Principal si el usuario ya ha iniciado sesión, si es así, lo redirigimos a la página de inicio
	   4.- Vamos a manejar los errores (Usuario no exista o error en la password) mediante @RequestParameter con el valor que no están enviado value="error"*/
	
	@GetMapping("/login")
	public String login(@RequestParam(name = "error", required = false) String error,
			@RequestParam(name = "logout", required = false) String logout,
			Model model, Principal principal, RedirectAttributes flash) {
	
		/* 1.- Principal() - Nos permite validar*/
		if (principal != null) {
			/* El usuario ya ha iniciado sesión, por tanto lo redirigimos a la página de inicio*/
			flash.addFlashAttribute("info", "!!!!! Ya has iniciado sesión anteriormente. !!!!!");
			return "redirect:/";
		}	
		if (error != null) {
			model.addAttribute("error", "Error en el login: Nombre de usuario o contraseña es incorrecta, por favor vuelva a conectar. !!!");
		}
		
		/* 2.- Cuando nos deslogueamos nos indica una url con la salida "logout" -> http://localhost:8081/login?logout 
	 	Vamos a capturarlo en la entrada del método @RequestParam y vamos a mandar un mensaje de despedida*/
	
		if(logout != null) {
		model.addAttribute("success", "Has cerrado la sesión con éxito.");
		}
		return "login";
	}
	
	@RequestMapping(value="/usuarioNuevo")
	public String usuarioNuevo(Map<String, Object> model) throws Exception {

		Usuario usuario = new Usuario();
		/* Pasamos los datos a la vista: */
		model.put("titulo", "Creación de Usuario.");
		model.put("usuario", usuario);
		
		clienteService.saveUsuario(usuario);
		return "usuarioNuevo";
	
	}

	@PostMapping("/usuarioNuevo")
	public String guardarUsuario(@Valid Usuario usuario,
			SessionStatus status) {

		clienteService.saveUsuario(usuario);

		// Cuando invoca este metodo borra la sesion
		status.setComplete();
		return "usuarioNuevo";
	}
		
}
