package org.iesalixar.servidor.controller;

import java.security.Principal;
import java.util.List;

import org.iesalixar.servidor.dto.PistaDTO;
import org.iesalixar.servidor.model.Pista;
import org.iesalixar.servidor.model.Usuario;
import org.iesalixar.servidor.services.PistaServiceImpl;
import org.iesalixar.servidor.services.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PistasController {

	@Autowired
	PistaServiceImpl pistaService;

	@Autowired
	UsuarioServiceImpl usuarioService;

	@RequestMapping("/pistas")
	public String pistas(Model model, Principal principal) {

		// Para mostrar nombre y apellidos del usuario que ha iniciado sesion
		Usuario user = usuarioService.getUsuarioByUserName(principal.getName());
		model.addAttribute("user", user);
		// -------------------------------------

		List<Pista> pista = pistaService.getAllPistas();

		model.addAttribute("pista", pista);
		return "pista/pistas";
	}

	@GetMapping("/pistas/addPista")
	public String addPistaGet(@RequestParam(required = false, name = "error") String error, Model model,
			Principal principal) {

		// Para mostrar nombre y apellidos del usuario que ha iniciado sesion
		Usuario user = usuarioService.getUsuarioByUserName(principal.getName());
		model.addAttribute("user", user);
		// -------------------------------------

		PistaDTO pistaDTO = new PistaDTO();
		model.addAttribute("pistaDTO", pistaDTO);
		model.addAttribute("error", error);

		return "pista/addPista";
	}

	@PostMapping("/pistas/addPista")
	public String addPistaPost(@ModelAttribute PistaDTO pistaDTO, RedirectAttributes atribute) {

		Pista pista = new Pista();
		pista.setNombre(pistaDTO.getNombre());
		pista.setDeporte(pistaDTO.getDeporte());
		pista.setApertura(pistaDTO.getApertura());
		pista.setCierre(pistaDTO.getCierre());

		if (pistaService.insertPista(pista) == null) {
			return "redirect:/pistas/addPista?error=Existe&NombrePista=" + pistaDTO.getNombre();
		}
		
		atribute.addFlashAttribute("success", "Pista ''" + pistaDTO.getNombre() + "'' guardada con éxito.");
		return "redirect:/pistas";

	}

	@GetMapping("/pistas/edit")
	public String editPistaGet(@RequestParam(name = "pist") String pist, Model model, Principal principal) {

		// Para mostrar nombre y apellidos del usuario que ha iniciado sesion
		Usuario user = usuarioService.getUsuarioByUserName(principal.getName());
		model.addAttribute("user", user);
		// -------------------------------------

		Pista pista = pistaService.findPistaByIdModel(Long.parseLong(pist));
		model.addAttribute("pista", pista);

		return "pista/editPista";
	}

	@PostMapping("/pistas/edit")
	public String updatePistaPost(@ModelAttribute Pista pi, RedirectAttributes atribute) {

		if (pistaService.actualizarPista(pi) == null) {
			return "redirect:/pistas/edit?error=error&pist" + pi.getId();
		}
		atribute.addFlashAttribute("edit", "Pista ''" + pi.getNombre() + "'' editada con éxito.");
		return "redirect:/pistas";
	}

	@GetMapping("/pistas/delete")
	public String eliminarPista(@RequestParam(required = true, name = "pist") String pist, Model model,
			Principal principal, RedirectAttributes atribute) {

		// Para mostrar nombre y apellidos del usuario que ha iniciado sesion
		Usuario user = usuarioService.getUsuarioByUserName(principal.getName());
		model.addAttribute("user", user);
		// -------------------------------------

		Pista pista = pistaService.findPistaByIdModel(Long.parseLong(pist));

		if (pista != null) {
			pistaService.eliminarPista(pista);
			atribute.addFlashAttribute("warning", "Pista ''" + pista.getNombre() + "'' eliminada con éxito.");
			return "redirect:/pistas?codigo=" + pist;
		} else {
			return "redirect:/pistas/";
		}
	}

}
