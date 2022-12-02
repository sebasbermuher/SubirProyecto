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
        .antMatchers("/","/login","/register").permitAll()
		.antMatchers("/usuarios","/usuarios/addUsuario","/usuarios/edit","/usuarios/delete").hasRole("ADMIN")
		.antMatchers("/pistas","/pistas/addPista","/pistas/edit","/pistas/delete").hasRole("ADMIN")
		.antMatchers("/reservas").hasRole("ADMIN")
		.antMatchers("/menu").authenticated()
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
        .logout()
            .permitAll()
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
