package org.iesalixar.servidor.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.iesalixar.servidor.dto.UsuarioDTO;
import org.iesalixar.servidor.model.Reserva;
import org.iesalixar.servidor.model.Usuario;
import org.iesalixar.servidor.services.ReservaServiceImpl;
import org.iesalixar.servidor.services.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsuariosController {

	@Autowired
	UsuarioServiceImpl usuarioService;

	@Autowired
	ReservaServiceImpl reService;

	@Autowired
	private JavaMailSender mailSender;

	@RequestMapping("/usuarios")
	public String usuarios(Model model, Principal principal) {

		// Para mostrar nombre y apellidos del usuario que ha iniciado sesion
		Usuario user = usuarioService.getUsuarioByUserName(principal.getName());
		model.addAttribute("user", user);
		// -------------------------------------

		List<Usuario> usuario = usuarioService.getAllUsuarios();

		model.addAttribute("usuario", usuario);
		return "usuario/usuarios";
	}

	@GetMapping("/usuarios/addUsuario")
	public String addUsuarioGet(@RequestParam(required = false, name = "errorUsername") String errorUsername,
			@RequestParam(required = false, name = "errorEmail") String errorEmail,
			@RequestParam(required = false, name = "errorDNI") String errorDNI,
			@RequestParam(required = false, name = "errorPassword") String errorPassword, Model model,
			Principal principal) {

		// Para mostrar nombre y apellidos del usuario que ha iniciado sesion
		Usuario user = usuarioService.getUsuarioByUserName(principal.getName());
		model.addAttribute("user", user);
		// -------------------------------------

		UsuarioDTO usuarioDTO = new UsuarioDTO();
		model.addAttribute("usuarioDTO", usuarioDTO);
		model.addAttribute("errorUsername", errorUsername);
		model.addAttribute("errorEmail", errorEmail);
		model.addAttribute("errorDNI", errorDNI);
		model.addAttribute("errorPassword", errorPassword);

		return "usuario/addUsuario";
	}

	@PostMapping("/usuarios/addUsuario")
	public String addUsuarioPost(@ModelAttribute UsuarioDTO usuarioDTO, RedirectAttributes atribute) {

		Usuario usuario = new Usuario();
		usuario.setNif(usuarioDTO.getNif());
		usuario.setNombre(usuarioDTO.getNombre());
		usuario.setApellido1(usuarioDTO.getApellido1());
		usuario.setApellido2(usuarioDTO.getApellido2());
		usuario.setEmail(usuarioDTO.getEmail());
		usuario.setUsername(usuarioDTO.getUsername());
		usuario.setPassword(new BCryptPasswordEncoder(15).encode(usuarioDTO.getPassword()));
		usuario.setLocalidad(usuarioDTO.getLocalidad());
		usuario.setProvincia(usuarioDTO.getProvincia());
		usuario.setTelefono(usuarioDTO.getTelefono());
		usuario.setSexo(usuarioDTO.getSexo());
		usuario.setRole(usuarioDTO.getRole());
		usuario.setFecha_nacimiento(usuarioDTO.getFecha_nacimiento());

		String fechaActual = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
		usuario.setFecha_registro(fechaActual);

		SimpleMailMessage email = new SimpleMailMessage();

//		if (usuarioService.insertUsuario(usuario) == null) {
//			return "redirect:/usuarios/addUsuario?error=Existe&Usuario=" + usuarioDTO.getUsername();
//		}

		if (usuarioDTO.getPassword().length() < 5) {
			atribute.addFlashAttribute("success", "La contraseña tiene que tener mínimo 5 caracteres.");
			return "redirect:/usuarios/addUsuario?errorPassword=min5&caracters";
		} else {
			usuario = usuarioService.insertUsuario(usuario);

			email.setTo(usuario.getEmail());
			email.setSubject("Registro en ReservaLaPista confirmado.");
			email.setText("Estimado/a " + usuario.getNombre() + " " + usuario.getApellido1() + " "
					+ usuario.getApellido2()
					+ ", \nle damos la bienvenida a nuestra aplicación web ''ReservaLaPista''. \nDesde ya puedes reservar nuestras pistas deportivas. \nMuchas gracias por su confianza. \nLe mandamos un cordial saludo. \nReservaLaPista.");

			mailSender.send(email);
		}

		while (usuario == null) {
			if (usuarioService.getUsuarioByUserName(usuarioDTO.getUsername()) != null) {
				atribute.addFlashAttribute("success", "Usuario no disponible");
				return "redirect:/usuarios/addUsuario?errorUsername=Existe&usuario=" + usuarioDTO.getUsername();
			}
			if (usuarioService.getUsuarioByEmail(usuarioDTO.getEmail()) != null) {
				atribute.addFlashAttribute("success", "Este email ya esta registrado.");
				return "redirect:/usuarios/addUsuario?errorEmail=Email&registrado";
			}
			if (usuarioService.getUsuarioByNif(usuarioDTO.getNif()) != null) {
				atribute.addFlashAttribute("success", "Ya existe una cuenta con este DNI.");
				return "redirect:/usuarios/addUsuario?errorDNI=dni&registrado=" + usuarioDTO.getNif();
			}
		}

		atribute.addFlashAttribute("success", "Usuario ''" + usuarioDTO.getUsername() + "'' guardado con éxito.");

		return "redirect:/usuarios";
	}

	@GetMapping("/usuarios/edit")
	public String editUsuarioGet(@RequestParam(name = "user") String user,
			@RequestParam(required = false, name = "errorEmail") String errorEmail, Model model, Principal principal) {

		// Para mostrar nombre y apellidos del usuario que ha iniciado sesion
		Usuario user2 = usuarioService.getUsuarioByUserName(principal.getName());
		model.addAttribute("user", user2);
		// -------------------------------------

		Usuario usuario = usuarioService.findUsuarioByIdModel(Long.parseLong(user));
		model.addAttribute("usuario", usuario);

		model.addAttribute("errorEmail", errorEmail);

		return "usuario/editUsuario";
	}

	@PostMapping("/usuarios/edit")
	public String updateUsuarioPost(@ModelAttribute Usuario usu, RedirectAttributes atribute) {

		Set<Reserva> re = reService.ReservaUsuario(usu);

		Usuario usuario = new Usuario();
		usuario.setId(usu.getId());
		usuario.setNombre(usu.getNombre());
		usuario.setApellido1(usu.getApellido1());
		usuario.setApellido2(usu.getApellido2());
		usuario.setUsername(usu.getUsername());
		usuario.setPassword(usu.getPassword());
		usuario.setEmail(usu.getEmail());
		usuario.setRole(usu.getRole());
		usuario.setNif(usu.getNif());
		usuario.setLocalidad(usu.getLocalidad());
		usuario.setProvincia(usu.getProvincia());
		usuario.setTelefono(usu.getTelefono());
		usuario.setSexo(usu.getSexo());
		usuario.setFecha_nacimiento(usu.getFecha_nacimiento());
		usuario.setFecha_registro(usu.getFecha_registro());
		usuario.setReserva(re);

		if (usuarioService.actualizarUsuario(usuario) == null) {
			return "redirect:/usuarios/edit?error=error&user" + usu.getId();
		}

		atribute.addFlashAttribute("edit", "Usuario ''" + usu.getUsername() + "'' editado con éxito.");
		return "redirect:/usuarios";
	}

	@GetMapping("/usuarios/info")
	public String infoUsuarioGet(@RequestParam(name = "user") String user,
			@RequestParam(required = false, name = "errorEmail") String errorEmail, Model model, Principal principal) {

		// Para mostrar nombre y apellidos del usuario que ha iniciado sesion
		Usuario user2 = usuarioService.getUsuarioByUserName(principal.getName());
		model.addAttribute("user", user2);
		// -------------------------------------

		Usuario usuario = usuarioService.findUsuarioByIdModel(Long.parseLong(user));
		model.addAttribute("usuario", usuario);

		model.addAttribute("errorEmail", errorEmail);

		return "usuario/infoUsuario";
	}

	@GetMapping("/usuarios/delete")
	public String eliminarUsuario(@RequestParam(required = true, name = "user") String user, Model model,
			RedirectAttributes atribute) {

		Usuario usuario = usuarioService.findUsuarioByIdModel(Long.parseLong(user));

		if (usuario != null) {
			usuarioService.eliminarUsuario(usuario);
			atribute.addFlashAttribute("warning", "Usuario ''" + usuario.getUsername() + "'' eliminado con éxito.");
			return "redirect:/usuarios?codigo=" + user;
		} else {
			return "redirect:/usuarios/";
		}
	}

	@GetMapping("/usuarios/reservas")
	public String reservaDeUsuario(@RequestParam(required = false, name = "codigo") String codigo,
			@RequestParam(required = false, name = "error") String error, Model model, Principal principal) {

		// Para mostrar nombre y apellidos del usuario que ha iniciado sesion
		Usuario user2 = usuarioService.getUsuarioByUserName(principal.getName());
		model.addAttribute("user", user2);
		// -------------------------------------
		if (codigo == null) {
			return "redirect:/usuarios/";
		}
		Optional<Usuario> usuario = usuarioService.findUsuarioById(Long.parseLong(codigo));
		model.addAttribute("usuario", usuario.get());
		if (usuario.get().getReserva().size() == 0 || usuario == null) {
			error = "error";
			model.addAttribute("error", error);
		}
		return "usuario/usuarioReservas";
	}

	@GetMapping("/usuarios/reservas/delete")
	public String eliminarReservaDeUsuario(@RequestParam(required = true, name = "rese") String rese, Model model,
			RedirectAttributes atribute) {
		SimpleMailMessage email = new SimpleMailMessage();
		Reserva reserva = reService.findReservaByIdModel(Long.parseLong(rese));

		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String fechaFormat = formatter.format(reserva.getFecha());

		email.setTo(reserva.getUsuario().getEmail());
		email.setSubject("Reserva cancelada.");
		email.setText("Estimado/a " + reserva.getUsuario().getNombre() + " " + reserva.getUsuario().getApellido1() + " "
				+ reserva.getUsuario().getApellido2() + ", \nle confirmamos que su reserva ha sido cancelada. \n"
				+ reserva.getPista().getNombre() + " --- " + reserva.getPista().getDeporte() + " \n" + fechaFormat
				+ " --- " + reserva.getHora_inicio() + "\nGracias por usar nuestros servicios. \nReservaLaPista");

		mailSender.send(email);
		reService.eliminarReserva(reserva);
		atribute.addFlashAttribute("warning", "Reserva eliminada con éxito.");

		return "redirect:/usuarios";

	}

}
