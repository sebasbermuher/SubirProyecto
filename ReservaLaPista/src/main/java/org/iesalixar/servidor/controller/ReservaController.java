package org.iesalixar.servidor.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.iesalixar.servidor.dto.ReservaDTO;
import org.iesalixar.servidor.model.Pista;
import org.iesalixar.servidor.model.Reserva;
import org.iesalixar.servidor.model.Usuario;
import org.iesalixar.servidor.services.PistaServiceImpl;
import org.iesalixar.servidor.services.ReservaServiceImpl;
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
public class ReservaController {

	@Autowired
	ReservaServiceImpl reservaService;

	@Autowired
	UsuarioServiceImpl usuarioService;

	@Autowired
	PistaServiceImpl pistaService;

	@Autowired
	private JavaMailSender mailSender;

	@GetMapping("/reservar")
	public String reservarGet(@RequestParam(required = false, name = "error") String error, Model model,
			Principal principal) {

		// Para mostrar nombre y apellidos del usuario que ha iniciado sesion
		Usuario user = usuarioService.getUsuarioByUserName(principal.getName());
		model.addAttribute("user", user);
		// -------------------------------------

		ReservaDTO reservaDTO = new ReservaDTO();
		List<Pista> pista = pistaService.getAllPistas();

		model.addAttribute("reservaDTO", reservaDTO);
		model.addAttribute("usuario", user);
		model.addAttribute("pista", pista);
		model.addAttribute("error", error);

		return "reserva/reservar";
	}

//	public List<Reserva> findReservas(Long idReserva, Date fechaReserva) {
//		return reservaService.findReservasByIdAndFecha(idReserva, fechaReserva);
//	}

	@PostMapping("/reservar")
	public String reservarPost(@ModelAttribute ReservaDTO reservaDTO, Model model, RedirectAttributes atribute) {

		Reserva reserva = new Reserva();
		reserva.setUsuario(reservaDTO.getId_usuario());
		reserva.setPista(reservaDTO.getId_pista());
		reserva.setFecha(reservaDTO.getFecha());
		reserva.setHora_inicio(reservaDTO.getHora_inicio());

		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String fechaFormat = formatter.format(reservaDTO.getFecha());

		SimpleMailMessage email = new SimpleMailMessage();

		if (reservaService.insertReserva(reserva) == null) {
			return "redirect:/reservar?error=Existe&Reserva=" + reservaDTO.getId_usuario() + reservaDTO.getId_pista();
		} else {
			email.setTo(reservaDTO.getId_usuario().getEmail());
			email.setSubject("Reserva confirmada.");
			email.setText("Estimado/a " + reservaDTO.getId_usuario().getNombre() + " "
					+ reservaDTO.getId_usuario().getApellido1() + " " + reservaDTO.getId_usuario().getApellido2()
					+ ", \nle confirmamos su reserva de la pista ''" + reservaDTO.getId_pista().getNombre() + " ("
					+ reservaDTO.getId_pista().getDeporte() + ")'' " + " para el día " + fechaFormat
					+ " con el siguiente tramo horario: " + reservaDTO.getHora_inicio()
					+ ". \nGracias por usar nuestros servicios. \nReservaLaPista");

			mailSender.send(email);
			atribute.addFlashAttribute("success", "Reserva realizada con éxito.");
			return "redirect:/misreservas";
		}

	}

	@RequestMapping("/reservas")
	public String todasReservas(Model model, Principal principal) {

		// Para mostrar nombre y apellidos del usuario que ha iniciado sesion
		Usuario user = usuarioService.getUsuarioByUserName(principal.getName());
		model.addAttribute("user", user);
		// -------------------------------------

		List<Reserva> reserva = reservaService.getAllReservas();

		model.addAttribute("reserva", reserva);

		return "reserva/totalReservas";
	}

//	MIS RESERVAS ----------------------------

	@RequestMapping("/misreservas")
	public String misReservas(Model model, Principal principal) {

		// Para mostrar nombre y apellidos del usuario que ha iniciado sesion
		Usuario user = usuarioService.getUsuarioByUserName(principal.getName());
		model.addAttribute("user", user);
		// -------------------------------------

		List<Reserva> reserva = reservaService.findReservaByUsuario(user);

		model.addAttribute("reserva", reserva);

		return "reserva/misReservas";
	}

//	----------------------------------------------------------------------------------

	@GetMapping("/reservas/addReserva")
	public String addReservaGet(@RequestParam(required = false, name = "error") String error, Model model,
			Principal principal) {

		// Para mostrar nombre y apellidos del usuario que ha iniciado sesion
		Usuario user = usuarioService.getUsuarioByUserName(principal.getName());
		model.addAttribute("user", user);
		// -------------------------------------

		ReservaDTO reservaDTO = new ReservaDTO();
		List<Usuario> usuario = usuarioService.getAllUsuarios();
		List<Pista> pista = pistaService.getAllPistas();

		model.addAttribute("reservaDTO", reservaDTO);
		model.addAttribute("usuario", usuario);
		model.addAttribute("pista", pista);
		model.addAttribute("error", error);

		return "reserva/addReserva";
	}

	@PostMapping("/reservas/addReserva")
	public String addReservaPost(@ModelAttribute ReservaDTO reservaDTO, Model model, RedirectAttributes atribute) {

		Reserva reserva = new Reserva();
		reserva.setUsuario(reservaDTO.getId_usuario());
		reserva.setPista(reservaDTO.getId_pista());
		reserva.setFecha(reservaDTO.getFecha());
		reserva.setHora_inicio(reservaDTO.getHora_inicio());

		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String fechaFormat = formatter.format(reservaDTO.getFecha());

		SimpleMailMessage email = new SimpleMailMessage();

		if (reservaService.insertReserva(reserva) == null) {
			return "redirect:/reservas/addReserva?error=Existe&Reserva=" + reservaDTO.getId_usuario()
					+ reservaDTO.getId_pista();
		} else {
			email.setTo(reservaDTO.getId_usuario().getEmail());
			email.setSubject("Reserva confirmada.");
			email.setText("Estimado/a " + reservaDTO.getId_usuario().getNombre() + " "
					+ reservaDTO.getId_usuario().getApellido1() + " " + reservaDTO.getId_usuario().getApellido2()
					+ ", \nle confirmamos su reserva de la pista ''" + reservaDTO.getId_pista().getNombre() + " ("
					+ reservaDTO.getId_pista().getDeporte() + ")'' " + " para el día " + fechaFormat
					+ " con el siguiente tramo horario: " + reservaDTO.getHora_inicio()
					+ ". \nGracias por usar nuestros servicios. \nReservaLaPista");

			mailSender.send(email);
			atribute.addFlashAttribute("success",
					"Reserva para el usuario  ''" + reservaDTO.getId_usuario().getNombre() + "'' realizada con éxito.");
			return "redirect:/reservas";
		}

	}

//	@GetMapping("/reservas/delete")
//	public String eliminarReserva(@RequestParam(required = true, name = "pista") Pista pista,
//			@RequestParam(required = true, name = "usuario") Usuario usuario, Model model) {
//		
//		Reserva reserva = reservaService.findUsuarioPistaById(usuario, pista);
//
//		if (reserva != null) {
//			reservaService.deleteUsuarioPistaById(reserva);
//			return "redirect:/reservas";
//
//		} else {
//			return "redirect:/reservas/";
//		}
//	}

}
