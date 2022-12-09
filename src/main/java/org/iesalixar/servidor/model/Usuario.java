package org.iesalixar.servidor.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, length = 9, nullable = false)
	private String nif;

	@Column(nullable = false)
	private String nombre;

	@Column(nullable = false)
	private String apellido1;

	@Column(nullable = false)
	private String apellido2;

	@Column(unique = true, nullable = false)
	private String email;

	@Column(name = "username", unique = true, nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String localidad;

	@Column(nullable = false)
	private String provincia;

	@Column(nullable = false, length = 9)
	private String telefono;

	@Column(nullable = false)
	private String sexo;

	@Column(nullable = false)
	private String role;

	@Column(nullable = false)
	private String fecha_nacimiento;

	@Column(nullable = false)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private String fecha_registro;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Reserva> reserva = new HashSet<>();


	public Usuario() {
		// TODO Auto-generated constructor stub
	}

	public Usuario(Long id, String nif, String nombre, String apellido1, String apellido2, String email,
			String username, String password, String localidad, String provincia, String telefono, String sexo,
			String role, String fecha_nacimiento, String fecha_registro, Set<Reserva> reserva) {
		super();
		this.id = id;
		this.nif = nif;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.email = email;
		this.username = username;
		this.password = password;
		this.localidad = localidad;
		this.provincia = provincia;
		this.telefono = telefono;
		this.sexo = sexo;
		this.role = role;
		this.fecha_nacimiento = fecha_nacimiento;
		this.fecha_registro = fecha_registro;
		this.reserva = reserva;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getFecha_nacimiento() {
		return fecha_nacimiento;
	}

	public void setFecha_nacimiento(String fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}

	public String getFecha_registro() {
		return fecha_registro;
	}

	public void setFecha_registro(String fecha_registro) {
		this.fecha_registro = fecha_registro;
	}

	public Set<Reserva> getReserva() {
		return reserva;
	}

	public void setReserva(Set<Reserva> reserva) {
		this.reserva = reserva;
	}
	
	

//	@Override
//	public int hashCode() {
//		return Objects.hash(apellido1, apellido2, email, fecha_nacimiento, fecha_registro, id, localidad, nif, nombre,
//				password, provincia, reserva, role, sexo, telefono, username);
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Usuario other = (Usuario) obj;
//		return Objects.equals(apellido1, other.apellido1) && Objects.equals(apellido2, other.apellido2)
//				&& Objects.equals(email, other.email) && Objects.equals(fecha_nacimiento, other.fecha_nacimiento)
//				&& Objects.equals(fecha_registro, other.fecha_registro) && Objects.equals(id, other.id)
//				&& Objects.equals(localidad, other.localidad) && Objects.equals(nif, other.nif)
//				&& Objects.equals(nombre, other.nombre) && Objects.equals(password, other.password)
//				&& Objects.equals(provincia, other.provincia) && Objects.equals(reserva, other.reserva)
//				&& Objects.equals(role, other.role) && Objects.equals(sexo, other.sexo)
//				&& Objects.equals(telefono, other.telefono) && Objects.equals(username, other.username);
//	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + "]";
	}

	// HELPERS ELIMINAR RESERVA
	public void removeReserva(Pista pista) {
		Reserva reserva = new Reserva();
		pista.getReserva().remove(reserva);
		this.reserva.remove(reserva);
	}

}
