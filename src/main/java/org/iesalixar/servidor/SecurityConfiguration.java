package org.iesalixar.servidor;

import org.iesalixar.servidor.services.JPAUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
 * CLASE DONDE ESTABLECEREMOS LA CONFIGURACION DE
 * AUTENTIFICACION - CÓMO ACCEDO
 * AUTORIZACION - A QUÉ PUEDO ACCEDER
 * MÉTODO DE ENCRIPTACIÓN DE LAS CONTRASEÑAS
 */

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	
	/* Obtengo una refencia al SINGLENTON del userDetailsService	 * 
	 */
	@Autowired
	JPAUserDetailsService userDetailsService;
	
	/* MÉTODO PARA AUTENTIFICAR LOS USUARIOS */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		//La autentificación JPA no está incluido tenemos que configurarla nosotros
		//Creando nuestro propio servicio que nos permita obtener la información del usuario
		auth.userDetailsService(userDetailsService);
	}

	/*
	 * MÉTODO PARA ESTABLECER AUTORIZACION - A QUÉ PUEDO ACCEDER
	 */
	

    String[] resources = new String[]{"/include/**","/css/**","/icons/**","/img/**","/js/**","/layer/**"};
	
    @Override
	protected void configure(HttpSecurity http) throws Exception {
		
		/* URL con información sobre ANT MATCHERS
		 * https://www.baeldung.com/spring-security-expressions */
    	
//		.antMatchers("/").permitAll()
//		.antMatchers("/menu").authenticated()
//		.antMatchers("/profesores","/profesores/asignaturas").authenticated()
//		.antMatchers("/profesores/addProfesor","/profesores/addAsignatura").hasRole("ADMIN")
		
    	http.authorizeRequests()
        .antMatchers(resources).permitAll()  
        //El usuario que no este registrado solo podrá ver estas paginas
        .antMatchers("/","/login","/register").permitAll()
        //autorizamos solo a los usuarios con role admin para las siguiente direcciones (pantallas admin)
		.antMatchers("/usuarios","/usuarios/addUsuario","/usuarios/edit","/usuarios/delete", "/usuarios/info","/usuarios/reservas","/usuarios/reservas/delete").hasRole("ADMIN")
		.antMatchers("/pistas","/pistas/addPista","/pistas/edit","/pistas/delete","/pistas/reservas","/pistas/reservas/delete").hasRole("ADMIN")
		.antMatchers("/reservas","/reservas/addReserva", "reservas/delete").hasRole("ADMIN")
            .anyRequest().authenticated()
            .and()
        .formLogin()
            .loginPage("/login")
            .permitAll()
            .defaultSuccessUrl("/menu")
            .failureUrl("/login?error=true")
            .usernameParameter("username")
            .passwordParameter("password")
            .and()
            .csrf().disable()
        .logout()
            .permitAll()
            // se elimina la cookie de session y nos redirige al login con el mensaje de cierre de sesion
            .deleteCookies("JSESSIONID")
            .logoutSuccessUrl("/login?logout");
	}
	
	
	/*
	 * ESTABLECEMOS EL PASSWORD ENCODER. FUERZA 15 (de 4 a 31)
	 */
	@Bean
    public PasswordEncoder getPasswordEncoder() {         
		return new BCryptPasswordEncoder(15);
    }
	
}
