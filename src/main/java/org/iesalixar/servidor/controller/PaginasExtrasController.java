package org.iesalixar.servidor.controller;

import java.security.Principal;
import java.util.List;

import org.iesalixar.servidor.model.Pista;
import org.iesalixar.servidor.model.Usuario;
import org.iesalixar.servidor.services.PistaServiceImpl;
import org.iesalixar.servidor.services.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaginasExtrasController {

	@Autowired
	UsuarioServiceImpl usuarioService;

	@Autowired
	PistaServiceImpl pistaService;

//	-----------------------------------------------------
//	HORARIOS
//	-----------------------------------------------------
	@GetMapping("/horarios")
	public String patrocinadores(Model model, Principal principal) {
		// Para mostrar nombre y apellidos del usuario que ha iniciado sesion
		Usuario user = usuarioService.getUsuarioByUserName(principal.getName());
		model.addAttribute("user", user);
		// -------------------------------------

		// recogemos en una lista todas las pistas
		List<Pista> pista = pistaService.getAllPistas();
		// creamos modelos para que desde el html pueda coger los datos de las pistas
		model.addAttribute("pista", pista);

		return "paginasExtras/horarios";
	}

//	-----------------------------------------------------
//	UBICACION
//	-----------------------------------------------------
	@GetMapping("/ubicacion")
	public String ubicacion(Model model, Principal principal) {
		// Para mostrar nombre y apellidos del usuario que ha iniciado sesion
		Usuario user = usuarioService.getUsuarioByUserName(principal.getName());
		model.addAttribute("user", user);
		// -------------------------------------

		// Nos devuelve la direccion donde se encuentra la plantilla html
		return "paginasExtras/ubicacion";
	}

//	-----------------------------------------------------
//	SOBRE NOSOTROS
//	-----------------------------------------------------
	@GetMapping("/sobrenosotros")
	public String sobrenosotros(Model model, Principal principal) {
		// Para mostrar nombre y apellidos del usuario que ha iniciado sesion
		Usuario user = usuarioService.getUsuarioByUserName(principal.getName());
		model.addAttribute("user", user);
		// -------------------------------------

		// Nos devuelve la direccion donde se encuentra la plantilla html
		return "paginasExtras/sobrenosotros";
	}
}
