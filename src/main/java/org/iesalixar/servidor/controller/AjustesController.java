package org.iesalixar.servidor.controller;

import java.security.Principal;
import java.util.Set;

import org.iesalixar.servidor.model.Reserva;
import org.iesalixar.servidor.model.Usuario;
import org.iesalixar.servidor.services.PistaServiceImpl;
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
public class AjustesController {

	@Autowired
	PistaServiceImpl pistaService;

	@Autowired
	UsuarioServiceImpl usuarioService;

	@Autowired
	ReservaServiceImpl reService;

	@Autowired
	private JavaMailSender mailSender;

//	-----------------------------------------------------
//	AJUSTES DE USUARIO
//	-----------------------------------------------------
	@GetMapping("/ajustes")
	public String ajustesDatosGet(@RequestParam(required = false, name = "error") String error, Model model,
			Principal principal) {

		// Para mostrar nombre y apellidos del usuario que ha iniciado sesion
		Usuario user2 = usuarioService.getUsuarioByUserName(principal.getName());
		model.addAttribute("user", user2);
		// -------------------------------------

		// creamos modelos para recoger el usuario que tiene iniciada sesion, y para
		// mostrar error (string)
		model.addAttribute("usuario", user2);
		model.addAttribute("error", error);

		return "ajustes/ajustes";
	}

	@PostMapping("/ajustes")
	public String ajustesDatosPost(@ModelAttribute Usuario usu, RedirectAttributes atribute) {

		// recogemos las reservas del usuario, para que cuando editemos el usuario,
		// tambien se actualice en los datos usuarios de la reserva
		Set<Reserva> re = reService.ReservaUsuario(usu);

		// creamos el usuario y le introducimos los datos
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

		// nos redirecciona a error si el usuario no esta bien editado
		if (usuarioService.actualizarUsuario(usuario) == null) {
			atribute.addFlashAttribute("warning", "Error al editar el usuario.");
			return "redirect:/ajustes?error=error&user";
		}
		// si todo ha ido bien, nos redirecciona con mensaje de exito
		atribute.addFlashAttribute("edit", "Usuario ''" + usu.getUsername() + "'' editado con éxito.");
		return "redirect:/ajustes";
	}

//	-----------------------------------------------------
//	CAMBIAR CONTRASEÑA
//	-----------------------------------------------------

	@GetMapping("/ajustes/cambiarPassw")
	public String cambiarPasswGet(@RequestParam(required = false, name = "error") String error, Model model,
			Principal principal) {

		// Para mostrar nombre y apellidos del usuario que ha iniciado sesion
		Usuario user = usuarioService.getUsuarioByUserName(principal.getName());
		model.addAttribute("user", user);
		// -------------------------------------

		// creamos modelos para recoger el usuario que tiene iniciada sesion, y para
		// mostrar error (string)
		model.addAttribute("usuario", user);
		model.addAttribute("error", error);

		return "ajustes/cambiarPassw";
	}

	@PostMapping("/ajustes/cambiarPassw")
	public String cambiarPasswPost(@ModelAttribute Usuario usu, RedirectAttributes atribute) {

		// recogemos las reservas del usuario, para que cuando editemos el usuario,
		// tambien se actualice en los datos usuarios de la reserva
		Set<Reserva> re = reService.ReservaUsuario(usu);

		// creamos el usuario y le introducimos los datos
		Usuario usuario = new Usuario();
		usuario.setId(usu.getId());
		usuario.setNombre(usu.getNombre());
		usuario.setApellido1(usu.getApellido1());
		usuario.setApellido2(usu.getApellido2());
		usuario.setUsername(usu.getUsername());
		usuario.setPassword(new BCryptPasswordEncoder(15).encode(usu.getPassword()));
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

		// si el tamaño de la cotraseña es menor de 5 nos mostrara un error
		if (usu.getPassword().length() < 5) {
			atribute.addFlashAttribute("warning", "La contraseña tiene que tener mínimo 5 caracteres.");
			return "redirect:/ajustes/cambiarPassw?error=min5&caracters";
		}

		// si al actualizar el usuario es null nos mostrara error
		if (usuarioService.actualizarUsuario(usuario) == null) {
			atribute.addFlashAttribute("warning", "Error al editar el usuario.");
			return "redirect:/ajustes/cambiarPassw?error=error&user";
		}

		// si todo ha ido bien, nos redirecciona con mensaje de exito
		atribute.addFlashAttribute("edit", "Su contraseña se ha actualizado correctamente.");
		return "redirect:/ajustes/cambiarPassw";
	}

//	-----------------------------------------------------
//	ELIMINAR CUENTA
//	-----------------------------------------------------

	@RequestMapping("/ajustes/cuenta")
	public String ajustesCuenta(Model model, Principal principal) {
		// Para mostrar nombre y apellidos del usuario que ha iniciado sesion
		Usuario user = usuarioService.getUsuarioByUserName(principal.getName());
		model.addAttribute("user", user);
		// -------------------------------------
		return "ajustes/cuenta";
	}

	@GetMapping("/ajustes/eliminarCuenta")
	public String eliminarUsuario(Model model, RedirectAttributes atribute, Principal principal) {

		SimpleMailMessage email = new SimpleMailMessage();
		// cogemos el username del usuario en sesion mediante la clase 'principal'
		Usuario usuario = usuarioService.getUsuarioByUserName(principal.getName());

		// si el usuario no es nulo, enviamos un email al email del usuario
		if (usuario != null) {
			email.setTo(usuario.getEmail());
			email.setSubject("Confirmación cuenta eliminada.");
			email.setText("Estimado/a " + usuario.getNombre() + " " + usuario.getApellido1() + " "
					+ usuario.getApellido2()
					+ ", \nle confirmamos que su cuenta de ReservaLaPista ha sido eliminada. \nGracias por usar nuestros servicios. \nReservaLaPista");

			mailSender.send(email);

			// eliminamos el usuario
			usuarioService.eliminarUsuario(usuario);
			// no redirecciona a logout
			return "redirect:/logout";
		} else {
			return "redirect:/ajustes/eliminarCuenta/";
		}
	}

}