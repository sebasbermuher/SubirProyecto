package org.iesalixar.servidor.dto;

import org.springframework.format.annotation.DateTimeFormat;

public class PistaDTO {
	private String nombre;
	private String deporte;
	@DateTimeFormat(pattern = "hh:mm tt")
	private String apertura;
	@DateTimeFormat(pattern = "hh:mm tt")
	private String cierre;
	@DateTimeFormat(pattern = "hh:mm tt")
	private String duracion;

	public PistaDTO() {
		// TODO Auto-generated constructor stub
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

	public String getDuracion() {
		return duracion;
	}

	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}

}
