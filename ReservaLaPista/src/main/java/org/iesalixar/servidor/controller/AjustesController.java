package org.iesalixar.servidor.controller;

import java.security.Principal;

import org.iesalixar.servidor.model.Usuario;
import org.iesalixar.servidor.services.PistaServiceImpl;
import org.iesalixar.servidor.services.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AjustesController {

	@Autowired
	PistaServiceImpl pistaService;

	@Autowired
	UsuarioServiceImpl usuarioService;
	
	
	@GetMapping("/ajustes")
	public String ajustesGet(@RequestParam(required = false, name = "error") String error, Model model,
			Principal principal) {

		// Para mostrar nombre y apellidos del usuario que ha iniciado sesion
		Usuario user = usuarioService.getUsuarioByUserName(principal.getName());
		model.addAttribute("user", user);
		// -------------------------------------

//		Pista pista = pistaService.findPistaByIdModel(Long.parseLong(pist));
//		model.addAttribute("pista", pista);
		
		
//		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		 UserDetails userDetail = (UserDetails) auth.getPrincipal();
//		 Usuario usuario = this.usuarioService.getUsuarioByEmail(userDetail.getUsername());
//		 System.out.println("ESTO");
////		    System.out.println(usuario);
//		    System.out.println(usuario.getNombre());
//		    System.out.println("EMAIL"+usuario.getEmail());
		
//		Usuario usuario = usuarioService.findUsuarioByIdModel(user);

		return "ajustes/ajustes";
	}
	
	
}
