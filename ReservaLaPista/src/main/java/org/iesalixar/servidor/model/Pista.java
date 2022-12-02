package org.iesalixar.servidor.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "pistas")
public class Pista implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String nombre;

	@Column(nullable = false)
	private String deporte;

	@Column(nullable = false)
	private String apertura;

	@Column(nullable = false)
	private String cierre;

	@OneToMany(mappedBy = "pista", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Reserva> reserva = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDeporte() {
		return deporte;
	}

	public void setDeporte(String deporte) {
		this.deporte = deporte;
	}

	public String getApertura() {
		return apertura;
	}

	public void setApertura(String apertura) {
		this.apertura = apertura;
	}

	public String getCierre() {
		return cierre;
	}

	public void setCierre(String cierre) {
		this.cierre = cierre;
	}

	public Set<Reserva> getReserva() {
		return reserva;
	}

	public void setReserva(Set<Reserva> reserva) {
		this.reserva = reserva;
	}

	@Override
	public int hashCode() {
		return Objects.hash(apertura, cierre, deporte, id, nombre, reserva);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pista other = (Pista) obj;
		return Objects.equals(apertura, other.apertura) && Objects.equals(cierre, other.cierre)
				&& Objects.equals(deporte, other.deporte) && Objects.equals(id, other.id)
				&& Objects.equals(nombre, other.nombre) && Objects.equals(reserva, other.reserva);
	}

	// HELPERS ELIMINAR RESERVA
	public void removeReserva(Usuario usuario) {
		Reserva reserva = new Reserva();
		usuario.getReserva().remove(reserva);
		this.reserva.remove(reserva);
	}

}
