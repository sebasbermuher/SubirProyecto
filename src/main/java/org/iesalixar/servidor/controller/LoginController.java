package org.iesalixar.servidor.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.iesalixar.servidor.dto.UsuarioDTO;
import org.iesalixar.servidor.model.Usuario;
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

@Controller
public class LoginController {

	@Autowired
	UsuarioServiceImpl usuarioService;

	@Autowired
	private JavaMailSender mailSender;

//	-----------------------------------------------------
//	LOGIN
//	-----------------------------------------------------
	// Mapeado multiple del login
	@RequestMapping({ "/", "/login" })
	public String login(Model model, Principal principal) {
		// Para mostrar nombre y apellidos del usuario que ha iniciado sesion
		if (principal != null) {
			Usuario user = usuarioService.getUsuarioByUserName(principal.getName());
			model.addAttribute("user", user);
		}
		// -------------------------------------
		return "login/index";
	}
	
//	-----------------------------------------------------
//	REGISTRO
//	-----------------------------------------------------
	@GetMapping("/register")
	public String registerGet(@RequestParam(required = false, name = "errorUsername") String errorUsername,
			@RequestParam(required = false, name = "errorEmail") String errorEmail,
			@RequestParam(required = false, name = "errorDNI") String errorDNI,
			@RequestParam(required = false, name = "errorPassword") String errorPassword, Model model) {

		UsuarioDTO userDTO = new UsuarioDTO();
		// Creacion de las variables
		model.addAttribute("userDTO", userDTO);
		model.addAttribute("errorUsername", errorUsername);
		model.addAttribute("errorEmail", errorEmail);
		model.addAttribute("errorDNI", errorDNI);
		model.addAttribute("errorPassword", errorPassword);

		return "login/register";
	}

	@PostMapping("/register")
	public String registerPost(@ModelAttribute UsuarioDTO usuario) {

		// Creacion de Usuario
		Usuario userBD = new Usuario();
		// Se añade al usuario los getters que recoge los datos
		userBD.setNif(usuario.getNif());
		userBD.setNombre(usuario.getNombre());
		userBD.setApellido1(usuario.getApellido1());
		userBD.setApellido2(usuario.getApellido2());
		userBD.setEmail(usuario.getEmail());
		userBD.setUsername(usuario.getUsername());
		userBD.setPassword(new BCryptPasswordEncoder(15).encode(usuario.getPassword()));
		userBD.setLocalidad(usuario.getLocalidad());
		userBD.setProvincia(usuario.getProvincia());
		userBD.setTelefono(usuario.getTelefono());
		userBD.setSexo(usuario.getSexo());
		userBD.setRole("ROLE_USER");
		userBD.setFecha_nacimiento(usuario.getFecha_nacimiento());

		// Generamos la fecha actual y se la pasamos a nuestro usuario como
		// fecha_registro
		String fechaActual = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
		userBD.setFecha_registro(fechaActual);

		// Inicializamos la clase email email
		SimpleMailMessage email = new SimpleMailMessage();

		// Si el username introducido ya está registrado en la base de datos, nos
		// muestra un error
		if (usuarioService.getUsuarioByUserName(usuario.getUsername()) != null) {
			return "redirect:/register?errorUsername=Existe&usuario";
		}
		// Si el email introducido ya está registrado en la base de datos, nos muetra un
		// error
		if (usuarioService.getUsuarioByEmail(usuario.getEmail()) != null) {
			return "redirect:/register?errorEmail=Email&registrado";
		}
		// Si el nif introducido ya está registrado en la base de datos, nos muetra un
		// error
		if (usuarioService.getUsuarioByNif(usuario.getNif()) != null) {
			return "redirect:/register?errorDNI=dni&registrado";
		}

		// Mientras que el usuario no sea nulo (es decir, se crea correctamente)
		if (userBD != null) {
			// Si la contraseña es menor de 5, nos muestra un error
			if (usuario.getPassword().length() < 5) {
				return "redirect:/register?errorPassword=min5&caracters";
			} else {
				// Si la contraseña es igual o mayor de 5, nos inserta el usuario en la base de
				// datos
				userBD = usuarioService.insertUsuario(userBD);

				// Además de insertar el usuario, le enviamos un email de bienvenida al email
				// del usuario
				email.setTo(usuario.getEmail());
				email.setSubject("Registro en ReservaLaPista confirmado.");
				email.setText("Estimado/a " + usuario.getNombre() + " " + usuario.getApellido1() + " "
						+ usuario.getApellido2()
						+ ", \nle damos la bienvenida a nuestra aplicación web ''ReservaLaPista''. \nDesde ya puedes reservar nuestras pistas deportivas. \nMuchas gracias por su confianza. \nLe mandamos un cordial saludo. \nReservaLaPista.");

				mailSender.send(email);
			}
		}
		// Una vez registrado nuestra cuenta, nos redirige al login
		return "redirect:/";
	}

	
//	-----------------------------------------------------
//	PANTALLA MENU
//	-----------------------------------------------------
	@RequestMapping({ "/menu" })
	public String menu(Model model, Principal principal) {

		// Para mostrar nombre y apellidos del usuario que ha iniciado sesion
		// Fuente --> http://www.it.uc3m.es/jaf/aw/practicas/5-spring/
		Usuario user = usuarioService.getUsuarioByUserName(principal.getName());
		model.addAttribute("user", user);

		return "menu/menu";
	}
}
