package org.iesalixar.servidor.model;
//package org.iesalixar.servidor.model;
//
//import java.io.Serializable;
//import java.util.Objects;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//import org.springframework.core.annotation.Order;
//
//@Entity
//@Table(name = "reserva")
//public class Reserva implements Serializable {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private int id_usuario;
//
//	@Column(nullable = false)
//	private int id_pista;
//
//	@Column(nullable = false)
//	private String fecha;
//
//	@Column(nullable = false)
//	private String hora_inicio;
//
//	@Column(nullable = false)
//	private String hora_fin;
//
//	public Reserva() {
//		// TODO Auto-generated constructor stub
//	}
//
//	public int getId_usuario() {
//		return id_usuario;
//	}
//
//	public void setId_usuario(int id_usuario) {
//		this.id_usuario = id_usuario;
//	}
//
//	public int getId_pista() {
//		return id_pista;
//	}
//
//	public void setId_pista(int id_pista) {
//		this.id_pista = id_pista;
//	}
//
//	public String getFecha() {
//		return fecha;
//	}
//
//	public void setFecha(String fecha) {
//		this.fecha = fecha;
//	}
//
//	public String getHora_inicio() {
//		return hora_inicio;
//	}
//
//	public void setHora_inicio(String hora_inicio) {
//		this.hora_inicio = hora_inicio;
//	}
//
//	public String getHora_fin() {
//		return hora_fin;
//	}
//
//	public void setHora_fin(String hora_fin) {
//		this.hora_fin = hora_fin;
//	}
//
//	@Override
//	public int hashCode() {
//		return Objects.hash(fecha, hora_fin, hora_inicio, id_pista, id_usuario);
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
//		Reserva other = (Reserva) obj;
//		return Objects.equals(fecha, other.fecha) && Objects.equals(hora_fin, other.hora_fin)
//				&& Objects.equals(hora_inicio, other.hora_inicio) && id_pista == other.id_pista
//				&& id_usuario == other.id_usuario;
//	}
//
//}
