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

//	-----------------------------------------------------
//	RESERVAR
//	-----------------------------------------------------
	@GetMapping("/reservar")
	public String reservarGet(@RequestParam(required = false, name = "error") String error, Model model,
			Principal principal) {

		// Para mostrar nombre y apellidos del usuario que ha iniciado sesion
		Usuario user = usuarioService.getUsuarioByUserName(principal.getName());
		model.addAttribute("user", user);
		// -------------------------------------

		// creamos un obejto de reservaDTO
		ReservaDTO reservaDTO = new ReservaDTO();
		// recogemos todas las pistas
		List<Pista> pista = pistaService.getAllPistas();

		// creamos los modelos
		model.addAttribute("reservaDTO", reservaDTO);
		model.addAttribute("usuario", user);
		model.addAttribute("pista", pista);
		model.addAttribute("error", error);

		return "reserva/reservar";
	}

	@PostMapping("/reservar")
	public String reservarPost(@ModelAttribute ReservaDTO reservaDTO, Model model, RedirectAttributes atribute) {

		// Creamos un objeto reserva y le introducimos los datos
		Reserva reserva = new Reserva();
		reserva.setUsuario(reservaDTO.getId_usuario());
		reserva.setPista(reservaDTO.getId_pista());
		reserva.setFecha(reservaDTO.getFecha());
		reserva.setHora_inicio(reservaDTO.getHora_inicio());

		// cambiamos el formato a la fecha
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String fechaFormat = formatter.format(reservaDTO.getFecha());

		SimpleMailMessage email = new SimpleMailMessage();

		// si el insert de la reserva es nula nos muestra un error
		if (reservaService.insertReserva(reserva) == null) {
			return "redirect:/reservar?error=Existe&Reserva=";
		}

		// si el insert de reserva no es nulo:
		// se crea la reserva (inserta)
		// enviamos un correo al email de usuario
		email.setTo(reservaDTO.getId_usuario().getEmail());
		email.setSubject("Reserva confirmada.");
		email.setText("Estimado/a " + reservaDTO.getId_usuario().getNombre() + " "
				+ reservaDTO.getId_usuario().getApellido1() + " " + reservaDTO.getId_usuario().getApellido2()
				+ ", \nle confirmamos su reserva de la pista ''" + reservaDTO.getId_pista().getNombre() + " ("
				+ reservaDTO.getId_pista().getDeporte() + ")'' " + " para el día " + fechaFormat
				+ " con el siguiente tramo horario: " + reservaDTO.getHora_inicio()
				+ ". \nGracias por usar nuestros servicios. \nReservaLaPista");

		mailSender.send(email);

		// no muestra mensaje de confirmacion en pantalla de 'Mis reservas'
		atribute.addFlashAttribute("success", "Reserva realizada con éxito.");
		// nos redirecciona a la pantalla de 'Mis reservas'
		return "redirect:/misreservas";
	}

//	-----------------------------------------------------
//	RESERVAS (TODAS)
//	-----------------------------------------------------
	@RequestMapping("/reservas")
	public String todasReservas(Model model, Principal principal) {

		// Para mostrar nombre y apellidos del usuario que ha iniciado sesion
		Usuario user = usuarioService.getUsuarioByUserName(principal.getName());
		model.addAttribute("user", user);
		// -------------------------------------

		// recogemos todas las reservas en un lista
		List<Reserva> reserva = reservaService.getAllReservas();

		// creamos modelos para que desde el html pueda coger los datos de las reservas
		model.addAttribute("reserva", reserva);

		return "reserva/totalReservas";
	}

//	-----------------------------------------------------
//	MIS RESERVAS
//	-----------------------------------------------------

	@RequestMapping("/misreservas")
	public String misReservas(Model model, Principal principal) {

		// Para mostrar nombre y apellidos del usuario que ha iniciado sesion
		Usuario user = usuarioService.getUsuarioByUserName(principal.getName());
		model.addAttribute("user", user);
		// -------------------------------------

		// recogemos en una lista todas las reservas del usuario
		// mediante el service busca la reserva del usuario
		List<Reserva> reserva = reservaService.findReservaByUsuario(user);

		// creamos modelos para que desde el html pueda coger los datos de las reservas
		model.addAttribute("reserva", reserva);

		return "reserva/misReservas";
	}

//	-----------------------------------------------------
//	ADD RESERVA
//	-----------------------------------------------------
	@GetMapping("/reservas/addReserva")
	public String addReservaGet(@RequestParam(required = false, name = "error") String error, Model model,
			Principal principal) {

		// Para mostrar nombre y apellidos del usuario que ha iniciado sesion
		Usuario user = usuarioService.getUsuarioByUserName(principal.getName());
		model.addAttribute("user", user);
		// -------------------------------------

		// Creamos un objeto de reservaDTO
		ReservaDTO reservaDTO = new ReservaDTO();
		// recogemos en una lista todos los usuarios
		List<Usuario> usuario = usuarioService.getAllUsuarios();
		// recogemos en una lista todos las pistas
		List<Pista> pista = pistaService.getAllPistas();

		// creamos modelos
		model.addAttribute("reservaDTO", reservaDTO);
		model.addAttribute("usuario", usuario);
		model.addAttribute("pista", pista);
		model.addAttribute("error", error);

		return "reserva/addReserva";
	}

	@PostMapping("/reservas/addReserva")
	public String addReservaPost(@ModelAttribute ReservaDTO reservaDTO, Model model, RedirectAttributes atribute) {

		// Creamos objeto de reserva y le introducimos los datos
		Reserva reserva = new Reserva();
		reserva.setUsuario(reservaDTO.getId_usuario());
		reserva.setPista(reservaDTO.getId_pista());
		reserva.setFecha(reservaDTO.getFecha());
		reserva.setHora_inicio(reservaDTO.getHora_inicio());

		// cambiamos el formato a la fecha
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String fechaFormat = formatter.format(reservaDTO.getFecha());

		SimpleMailMessage email = new SimpleMailMessage();

		// si el insert de reserva es nulo, nos muestra un error
		if (reservaService.insertReserva(reserva) == null) {
			return "redirect:/reservas/addReserva?error=Existe&Reserva=" + reservaDTO.getId_usuario()
					+ reservaDTO.getId_pista();
		} else {
			// si el insert de reserva no es nulo
			// nos crea la reserva(inserta)
			// envia un correo al email de usuario
			email.setTo(reservaDTO.getId_usuario().getEmail());
			email.setSubject("Reserva confirmada.");
			email.setText("Estimado/a " + reservaDTO.getId_usuario().getNombre() + " "
					+ reservaDTO.getId_usuario().getApellido1() + " " + reservaDTO.getId_usuario().getApellido2()
					+ ", \nle confirmamos su reserva de la pista ''" + reservaDTO.getId_pista().getNombre() + " ("
					+ reservaDTO.getId_pista().getDeporte() + ")'' " + " para el día " + fechaFormat
					+ " con el siguiente tramo horario: " + reservaDTO.getHora_inicio()
					+ ". \nGracias por usar nuestros servicios. \nReservaLaPista");

			mailSender.send(email);
			// nos redirecciona a la pantalla de todas las reservas y nos muestra un mensaje
			// de exito
			atribute.addFlashAttribute("success", "Reserva para el usuario  ''"
					+ reservaDTO.getId_usuario().getUsername() + "'' realizada con éxito.");
			return "redirect:/reservas";
		}

	}

//	-----------------------------------------------------
//	ELIMINAR RESERVA
//	-----------------------------------------------------
	@GetMapping("/reservas/delete")
	public String eliminarReserva(@RequestParam(required = true, name = "rese") String rese, Model model,
			RedirectAttributes atribute) {
		SimpleMailMessage email = new SimpleMailMessage();
		// recogemos la id de la reserva mediante service
		Reserva reserva = reservaService.findReservaByIdModel(Long.parseLong(rese));

		// si la id de la reserva no es nula nos elimina la reserva
		if (reserva != null) {
			// cambiamos formato a la fecha
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String fechaFormat = formatter.format(reserva.getFecha());

			// enviamos correo al email del usuario
			email.setTo(reserva.getUsuario().getEmail());
			email.setSubject("Reserva cancelada.");
			email.setText("Estimado/a " + reserva.getUsuario().getNombre() + " " + reserva.getUsuario().getApellido1()
					+ " " + reserva.getUsuario().getApellido2()
					+ ", \nle confirmamos que su reserva ha sido eliminada. \n" + reserva.getPista().getNombre()
					+ " --- " + reserva.getPista().getDeporte() + " \n" + fechaFormat + " --- "
					+ reserva.getHora_inicio() + "\nGracias por usar nuestros servicios. \nReservaLaPista");

			mailSender.send(email);
			// elimina la reserva mediante el service
			reservaService.eliminarReserva(reserva);
			// nos muestra mensaje
			atribute.addFlashAttribute("warning", "Reserva eliminada con éxito.");

			return "redirect:/reservas?codigo=" + rese;
		} else {
			return "redirect:/reservas/";
		}
	}

//	-----------------------------------------------------
//	ELIMINAR MI RESERVA
//	-----------------------------------------------------
	@GetMapping("/misreservas/delete")
	public String eliminarmisReserva(@RequestParam(required = true, name = "rese") String rese, Model model,
			RedirectAttributes atribute, Principal principal) {

		SimpleMailMessage email = new SimpleMailMessage();
		// recogemos la id del usuario mediante service, introduciendole el usuario
		// actual en sesion
		Usuario usuario = usuarioService.getUsuarioByUserName(principal.getName());
		// recogemos la id de la reserva mediante service
		Reserva reserva = reservaService.findReservaByIdModel(Long.parseLong(rese));

		// cambiamos el formato a la fecha
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String fechaFormat = formatter.format(reserva.getFecha());

		// enviamos correo al email del usuario
		email.setTo(usuario.getEmail());
		email.setSubject("Reserva cancelada.");
		email.setText("Estimado/a " + usuario.getNombre() + " " + usuario.getApellido1() + " " + usuario.getApellido2()
				+ ", \nle confirmamos que su reserva ha sido eliminada. \n" + reserva.getPista().getNombre() + " --- "
				+ reserva.getPista().getDeporte() + " \n" + fechaFormat + " --- " + reserva.getHora_inicio()
				+ "\nGracias por usar nuestros servicios. \nReservaLaPista");

		mailSender.send(email);
		// eliminamos la reserva
		reservaService.eliminarReserva(reserva);
		// mostramos mensaje
		atribute.addFlashAttribute("warning", "Su reserva se ha cancelado.");

		return "redirect:/misreservas?codigo=" + rese;

	}
}
