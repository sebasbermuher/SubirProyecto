package org.iesalixar.servidor.services;

import java.util.List;

import org.iesalixar.servidor.model.Pista;
import org.iesalixar.servidor.model.Reserva;
import org.iesalixar.servidor.model.Usuario;

public interface ReservaService {

	public Reserva insertReserva(Reserva reserva);

	public List<Reserva> getAllReservas();

//	public Reserva findUsuarioReservaById(Usuario id, Reserva id1);
//	public void deleteUsuarioReservaById(Reserva usuarioReserva);

	public Reserva findUsuarioPistaById(Usuario id, Pista id1);

	public void deleteUsuarioPistaById(Reserva reserva);

	public Reserva findReservaByIdModel(Long id);

	public Reserva eliminarReserva(Reserva reserva);

//	public List<Reserva> findReservasByIdAndFecha(Long id, Date fecha);
	
	public List<Reserva> findReservaByUsuario(Usuario usuario);

}
