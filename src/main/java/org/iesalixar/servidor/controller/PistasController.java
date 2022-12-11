package org.iesalixar.servidor.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.iesalixar.servidor.dto.PistaDTO;
import org.iesalixar.servidor.model.Pista;
import org.iesalixar.servidor.model.Reserva;
import org.iesalixar.servidor.model.Usuario;
import org.iesalixar.servidor.services.PistaServiceImpl;
import org.iesalixar.servidor.services.ReservaService;
import org.iesalixar.servidor.services.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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

	@Autowired
	ReservaService reService;

	@Autowired
	private JavaMailSender mailSender;

//	-----------------------------------------------------
//	PISTAS
//	-----------------------------------------------------
	@RequestMapping("/pistas")
	public String pistas(Model model, Principal principal) {

		// Para mostrar nombre y apellidos del usuario que ha iniciado sesion
		Usuario user = usuarioService.getUsuarioByUserName(principal.getName());
		model.addAttribute("user", user);
		// -------------------------------------
		// recogemos en una lista todas las pistas
		List<Pista> pista = pistaService.getAllPistas();

		// creamos modelos para que desde el html pueda coger los datos de las pistas
		model.addAttribute("pista", pista);

		return "pista/pistas";
	}

//	-----------------------------------------------------
//	AÑADIR PISTA
//	-----------------------------------------------------
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

		// creamos un objeto pista y le introducimos los datos (getter a los setter)
		Pista pista = new Pista();
		pista.setNombre(pistaDTO.getNombre());
		pista.setDeporte(pistaDTO.getDeporte());
		pista.setApertura(pistaDTO.getApertura());
		pista.setCierre(pistaDTO.getCierre());

		// Si la pista es nulo, nos devuelve un error
		if (pistaService.insertPista(pista) == null) {
			atribute.addFlashAttribute("success", "ERROR. Ya existe una pista con este nombre.");
			return "redirect:/pistas/addPista?error=Existe&NombrePista";
		}

		// si la pista no es nulo, se añade la pista correctamente
		atribute.addFlashAttribute("success",
				"Se ha añadido una nueva pista. ''" + pistaDTO.getNombre() + "'' guardada con éxito.");
		return "redirect:/pistas";

	}

//	-----------------------------------------------------
//	EDITAR PISTA
//	-----------------------------------------------------
	@GetMapping("/pistas/edit")
	public String editPistaGet(@RequestParam(name = "pist") String pist, Model model, Principal principal) {

		// Para mostrar nombre y apellidos del usuario que ha iniciado sesion
		Usuario user = usuarioService.getUsuarioByUserName(principal.getName());
		model.addAttribute("user", user);
		// -------------------------------------

		// cogemos la pista por el id introducido y creamos el modelo
		Pista pista = pistaService.findPistaByIdModel(Long.parseLong(pist));
		model.addAttribute("pista", pista);

		return "pista/editPista";
	}

	@PostMapping("/pistas/edit")
	public String updatePistaPost(@ModelAttribute Pista pi, RedirectAttributes atribute) {

		// recogemos las reservas de la pista, para que cuando editemos la pista,
		// tambien se actualice en los datos 'pista' de la reserva
		Set<Reserva> re = reService.findReservaByPista(pi);
		// creamos el objeto pista y le introducimos los datos
		Pista pista = new Pista();
		pista.setId(pi.getId());
		pista.setNombre(pi.getNombre());
		pista.setDeporte(pi.getDeporte());
		pista.setApertura(pi.getApertura());
		pista.setCierre(pi.getCierre());
		pista.setReserva(re);

		// si el nombre de la pista ya está guardado en la base de datos, nos mostrará
		// un error
		if (pistaService.getPistaByName(pi.getNombre()) != null) {
			atribute.addFlashAttribute("danger",
					"Error al modificar el nombre de la pista. Ya existe una pista llamada '" + pi.getNombre() + "'");
			return "redirect:/pistas";
		}

		// si la pista es nula no mostrara error
		if (pistaService.actualizarPista(pista) == null) {
			return "redirect:/pistas/edit?error=error&pist" + pi.getId();
		}

		// sino, se actualiza la pista correctamente (en las reservas tambien se
		// actualiza)
		atribute.addFlashAttribute("edit", "Pista ''" + pi.getNombre() + "'' editada con éxito.");
		return "redirect:/pistas";
	}

//	-----------------------------------------------------
//	ELIMINAR PISTA
//	-----------------------------------------------------
	@GetMapping("/pistas/delete")
	public String eliminarPista(@RequestParam(required = true, name = "pist") String pist, Model model,
			Principal principal, RedirectAttributes atribute) {

		// Para mostrar nombre y apellidos del usuario que ha iniciado sesion
		Usuario user = usuarioService.getUsuarioByUserName(principal.getName());
		model.addAttribute("user", user);
		// -------------------------------------

		// cogemo la id de la pista mediante service
		Pista pista = pistaService.findPistaByIdModel(Long.parseLong(pist));

		// si la pista no es nula se elimina, sino nos vuelve a redireccionar a la
		// pantalla pistas
		if (pista != null) {
			pistaService.eliminarPista(pista);
			atribute.addFlashAttribute("warning", "Pista ''" + pista.getNombre() + "'' eliminada con éxito.");
			return "redirect:/pistas?codigo=" + pist;
		} else {
			return "redirect:/pistas/";
		}
	}

//	-----------------------------------------------------
//	RESERVAS DE PISTA
//	-----------------------------------------------------
	@GetMapping("/pistas/reservas")
	public String reservaDePista(@RequestParam(required = false, name = "codigo") String codigo,
			@RequestParam(required = false, name = "error") String error, Model model, Principal principal) {

		// Para mostrar nombre y apellidos del usuario que ha iniciado sesion
		Usuario user2 = usuarioService.getUsuarioByUserName(principal.getName());
		model.addAttribute("user", user2);
		// -------------------------------------

		// creamos un objeto contenedor de pista con el valor de la id
		Optional<Pista> pista = pistaService.findPistaById(Long.parseLong(codigo));
		model.addAttribute("pista", pista.get());

		return "pista/pistaReservas";
	}

//	-----------------------------------------------------
//	ELIMINAR RESERVA DE PISTA
//	-----------------------------------------------------

	@GetMapping("/pistas/reservas/delete")
	public String eliminarReservaDePista(@RequestParam(required = true, name = "rese") String rese, Model model,
			RedirectAttributes atribute) {
		SimpleMailMessage email = new SimpleMailMessage();
		// cogemos la id de la pista mediante service
		Reserva reserva = reService.findReservaByIdModel(Long.parseLong(rese));

		// le cambiamos el formato a la fecha
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String fechaFormat = formatter.format(reserva.getFecha());

		// enviamos correo al email de usuario para confirmar la cancelacion de la
		// reserva
		email.setTo(reserva.getUsuario().getEmail());
		email.setSubject("Reserva cancelada.");
		email.setText("Estimado/a " + reserva.getUsuario().getNombre() + " " + reserva.getUsuario().getApellido1() + " "
				+ reserva.getUsuario().getApellido2() + ", \nle confirmamos que su reserva ha sido cancelada. \n"
				+ reserva.getPista().getNombre() + " --- " + reserva.getPista().getDeporte() + " \n" + fechaFormat
				+ " --- " + reserva.getHora_inicio() + "\nGracias por usar nuestros servicios. \nReservaLaPista");

		mailSender.send(email);
		// eliminamos la reserva y mostramos mensaje en /pistas
		reService.eliminarReserva(reserva);
		atribute.addFlashAttribute("warning", "Reserva eliminada con éxito.");

		return "redirect:/pistas";
	}

}
