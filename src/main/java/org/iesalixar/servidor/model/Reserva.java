package org.iesalixar.servidor.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "reserva", uniqueConstraints = @UniqueConstraint(columnNames = { "fecha", "hora_inicio" }))
//@IdClass(Reserva_id.class)
public class Reserva implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_pista")
	private Pista pista;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date fecha;

	@Column(name = "hora_inicio")
	private String hora_inicio;

	public Reserva() {
	}

	public Reserva(Usuario usuario, Pista pista, Date fecha, String hora_inicio) {
		super();
		this.usuario = usuario;
		this.pista = pista;
		this.fecha = fecha;
		this.hora_inicio = hora_inicio;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Pista getPista() {
		return pista;
	}

	public void setPista(Pista pista) {
		this.pista = pista;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getHora_inicio() {
		return hora_inicio;
	}

	public void setHora_inicio(String hora_inicio) {
		this.hora_inicio = hora_inicio;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reserva other = (Reserva) obj;
		return Objects.equals(fecha, other.fecha) && Objects.equals(hora_inicio, other.hora_inicio)
				&& Objects.equals(id, other.id) && Objects.equals(pista, other.pista)
				&& Objects.equals(usuario, other.usuario);
	}

}
