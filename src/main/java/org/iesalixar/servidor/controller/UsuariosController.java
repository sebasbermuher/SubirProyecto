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

//	-----------------------------------------------------
//	USUARIOS
//	-----------------------------------------------------
	@RequestMapping("/usuarios")
	public String usuarios(Model model, Principal principal) {

		// Para mostrar nombre y apellidos del usuario que ha iniciado sesion
		Usuario user = usuarioService.getUsuarioByUserName(principal.getName());
		model.addAttribute("user", user);
		// -------------------------------------
		// Recogemos en una lista todos los usuarios
		List<Usuario> usuario = usuarioService.getAllUsuarios();
		// creamos el modelo para que desde el html pueda coger los datos de los
		// usuarios
		model.addAttribute("usuario", usuario);
		return "usuario/usuarios";
	}

//	-----------------------------------------------------
//	AÑADIR USUARIO
//	-----------------------------------------------------
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

		// creamos objeto de usuarioDTO
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		// creamos los modelos para dto y los errores
		model.addAttribute("usuarioDTO", usuarioDTO);
		model.addAttribute("errorUsername", errorUsername);
		model.addAttribute("errorEmail", errorEmail);
		model.addAttribute("errorDNI", errorDNI);
		model.addAttribute("errorPassword", errorPassword);

		return "usuario/addUsuario";
	}

	@PostMapping("/usuarios/addUsuario")
	public String addUsuarioPost(@ModelAttribute UsuarioDTO usuarioDTO, RedirectAttributes atribute) {

		// creamos un objeto usuario y le introducimos los datos (getter a los setter)
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

		// cambiamos el formato a la fecha
		String fechaActual = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
		usuario.setFecha_registro(fechaActual);

		SimpleMailMessage email = new SimpleMailMessage();

		// si el username introducido ya está en la base de datos nos devuelve un error
		if (usuarioService.getUsuarioByUserName(usuarioDTO.getUsername()) != null) {
			atribute.addFlashAttribute("success", "Usuario no disponible");
			return "redirect:/usuarios/addUsuario?errorUsername=Existe&usuario";
		}
		// si el email introducido ya está en la base de datos nos devuelve un error
		if (usuarioService.getUsuarioByEmail(usuarioDTO.getEmail()) != null) {
			atribute.addFlashAttribute("success", "Este email ya esta registrado.");
			return "redirect:/usuarios/addUsuario?errorEmail=Email&registrado";
		}
		// si el dni introducido ya está en la base de datos nos devuelve un error
		if (usuarioService.getUsuarioByNif(usuarioDTO.getNif()) != null) {
			atribute.addFlashAttribute("success", "Ya existe una cuenta con este DNI.");
			return "redirect:/usuarios/addUsuario?errorDNI=dni&registrado";
		}

		// si el usuario no es nulo
		if (usuario != null) {
			// si el tamaño de la contraseña es menor de 5 nos devuelve un error
			if (usuarioDTO.getPassword().length() < 5) {
				atribute.addFlashAttribute("success", "La contraseña tiene que tener mínimo 5 caracteres.");
				return "redirect:/usuarios/addUsuario?errorPassword=min5&caracters";
			} else {
				// si el tamaño de la contraseña NO es menor de 5:
				// nos introduce el usuario
				// envia un correo al email del usuario introducido
				usuario = usuarioService.insertUsuario(usuario);

				email.setTo(usuario.getEmail());
				email.setSubject("Registro en ReservaLaPista confirmado.");
				email.setText("Estimado/a " + usuario.getNombre() + " " + usuario.getApellido1() + " "
						+ usuario.getApellido2()
						+ ", \nle damos la bienvenida a nuestra aplicación web ''ReservaLaPista''. \nDesde ya puedes reservar nuestras pistas deportivas. \nMuchas gracias por su confianza. \nLe mandamos un cordial saludo. \nReservaLaPista.");

				mailSender.send(email);
			}
		}

		// no redirecciona a 'USUARIOS' y nos muestra mensaje de exito
		atribute.addFlashAttribute("success", "Usuario ''" + usuarioDTO.getUsername() + "'' guardado con éxito.");

		return "redirect:/usuarios";
	}

//	-----------------------------------------------------
//	EDITAR USUARIO
//	-----------------------------------------------------
	@GetMapping("/usuarios/edit")
	public String editUsuarioGet(@RequestParam(name = "user") String user,
			@RequestParam(required = false, name = "errorEmail") String errorEmail, Model model, Principal principal) {

		// Para mostrar nombre y apellidos del usuario que ha iniciado sesion
		Usuario user2 = usuarioService.getUsuarioByUserName(principal.getName());
		model.addAttribute("user", user2);
		// -------------------------------------
		// cogemos el usuario por el id introducido y creamos el modelo
		Usuario usuario = usuarioService.findUsuarioByIdModel(Long.parseLong(user));
		model.addAttribute("usuario", usuario);

		model.addAttribute("errorEmail", errorEmail);

		return "usuario/editUsuario";
	}

	@PostMapping("/usuarios/edit")
	public String updateUsuarioPost(@ModelAttribute Usuario usu, RedirectAttributes atribute) {
		// recogemos las reservas de los usuario, para que cuando editemos el usuario,
		// tambien se actualice en los datos 'usuario' de la reserva
		Set<Reserva> re = reService.ReservaUsuario(usu);

		// creamos el objeto usuario y le introducimos los datos
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

		// si el usuario introducido es nulo nos devuelve un error
		if (usuarioService.actualizarUsuario(usuario) == null) {
			return "redirect:/usuarios/edit?error=error&user" + usu.getId();
		}

		// si no es nulo, se edita el usuario correctamente
		// nos redirecciona a usuario y no muestra un mensaje de exito
		atribute.addFlashAttribute("edit", "Usuario ''" + usu.getUsername() + "'' editado con éxito.");
		return "redirect:/usuarios";
	}

//	-----------------------------------------------------
//	INFO USUARIO
//	-----------------------------------------------------
	@GetMapping("/usuarios/info")
	public String infoUsuarioGet(@RequestParam(name = "user") String user, Model model, Principal principal) {

		// Para mostrar nombre y apellidos del usuario que ha iniciado sesion
		Usuario user2 = usuarioService.getUsuarioByUserName(principal.getName());
		model.addAttribute("user", user2);
		// -------------------------------------

		// cogemos el usuario por el id introducido
		Usuario usuario = usuarioService.findUsuarioByIdModel(Long.parseLong(user));
		// creamos modelos para que desde el html pueda coger los datos del usuario
		model.addAttribute("usuario", usuario);

		return "usuario/infoUsuario";
	}

//	-----------------------------------------------------
//	ELIMINAR USUARIO
//	-----------------------------------------------------
	@GetMapping("/usuarios/delete")
	public String eliminarUsuario(@RequestParam(required = true, name = "user") String user, Model model,
			RedirectAttributes atribute) {
		SimpleMailMessage email = new SimpleMailMessage();

		// cogemo la id del usuario mediante service
		Usuario usuario = usuarioService.findUsuarioByIdModel(Long.parseLong(user));

		// si el usuario no es nulo
		if (usuario != null) {
			// se envia un correo al email del usuario
			email.setTo(usuario.getEmail());
			email.setSubject("Confirmación cuenta eliminada.");
			email.setText("Estimado/a " + usuario.getNombre() + " " + usuario.getApellido1() + " "
					+ usuario.getApellido2()
					+ ", \nle confirmamos que su cuenta de ReservaLaPista ha sido eliminada. \nGracias por usar nuestros servicios. \nReservaLaPista");

			mailSender.send(email);
			// se elimina el usuario
			usuarioService.eliminarUsuario(usuario);
			// nos muestra un mensaje
			atribute.addFlashAttribute("warning", "Usuario ''" + usuario.getUsername() + "'' eliminado con éxito.");
			return "redirect:/usuarios?codigo=" + user;
		} else {
			// si el usuario es nulo nos devuele a la pantalla 'usuarios'
			return "redirect:/usuarios/";
		}
	}

//	-----------------------------------------------------
//	RESERVAS DE USUARIOS
//	-----------------------------------------------------
	@GetMapping("/usuarios/reservas")
	public String reservaDeUsuario(@RequestParam(required = false, name = "codigo") String codigo,
			@RequestParam(required = false, name = "error") String error, Model model, Principal principal) {

		// Para mostrar nombre y apellidos del usuario que ha iniciado sesion
		Usuario user2 = usuarioService.getUsuarioByUserName(principal.getName());
		model.addAttribute("user", user2);
		// -------------------------------------

		// creamos un objeto contenedor de usuario con el valor de la id
		Optional<Usuario> usuario = usuarioService.findUsuarioById(Long.parseLong(codigo));
		model.addAttribute("usuario", usuario.get());

		return "usuario/usuarioReservas";
	}

//	-----------------------------------------------------
//	ELIMINAR RESERVA DE USUARIO
//	-----------------------------------------------------
	@GetMapping("/usuarios/reservas/delete")
	public String eliminarReservaDeUsuario(@RequestParam(required = true, name = "rese") String rese, Model model,
			RedirectAttributes atribute) {
		SimpleMailMessage email = new SimpleMailMessage();
		// cogemos la id del usuario mediante service
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
		// eliminamos la reserva y mostramos mensaje en '/usuarios'
		reService.eliminarReserva(reserva);
		atribute.addFlashAttribute("warning", "Reserva eliminada con éxito.");

		return "redirect:/usuarios";

	}

}
