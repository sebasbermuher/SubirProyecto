package org.iesalixar.servidor.controller;

import java.security.Principal;

import org.iesalixar.servidor.model.Usuario;
import org.iesalixar.servidor.services.PistaServiceImpl;
import org.iesalixar.servidor.services.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AjustesController {

	@Autowired
	PistaServiceImpl pistaService;

	@Autowired
	UsuarioServiceImpl usuarioService;
	
	
//	@GetMapping("/ajustes")
//	public String ajustesGet(@RequestParam(required = false, name = "error") String error, Model model,
//			Principal principal) {
//
//		// Para mostrar nombre y apellidos del usuario que ha iniciado sesion
//		Usuario user = usuarioService.getUsuarioByUserName(principal.getName());
//		model.addAttribute("user", user);
//		// -------------------------------------
//
//
//		return "ajustes/ajustes";
//	}
//	
	@GetMapping("/ajustes")
	public String ajustesDatosGet(@RequestParam(required = false, name = "error") String error, Model model,
			Principal principal) {

		// Para mostrar nombre y apellidos del usuario que ha iniciado sesion
		Usuario user = usuarioService.getUsuarioByUserName(principal.getName());
		model.addAttribute("user", user);
		// -------------------------------------
		model.addAttribute("usuario", user);
		model.addAttribute("error", error);
		
		return "ajustes/ajustes";
	}
	
	
	@PostMapping("/ajustes")
	public String ajustesDatosPost(@ModelAttribute Usuario usu, RedirectAttributes atribute) {

		if (usuarioService.actualizarUsuario(usu) == null) {
			return "redirect:/ajustes?error=error&user";
		}
		atribute.addFlashAttribute("edit", "Sus datos se han actualizado correctamente.");
		return "redirect:/ajustes";
	}

}
