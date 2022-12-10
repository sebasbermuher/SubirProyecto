package org.iesalixar.servidor.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.iesalixar.servidor.model.Pista;
import org.iesalixar.servidor.model.Reserva;
import org.iesalixar.servidor.model.Usuario;

public interface ReservaService {

	public Reserva insertReserva(Reserva reserva);

	public List<Reserva> getAllReservas();

	public void deleteUsuarioPistaById(Reserva reserva);

	public Reserva findReservaByIdModel(Long id);

	public Reserva eliminarReserva(Reserva reserva);

	public List<Reserva> findReservaByUsuario(Usuario usuario);

	public Set<Reserva> findReservaByPista(Pista pista);

	public Set<Reserva> ReservaUsuario(Usuario usuario);

	public Optional<Reserva> findReservaById(Long id);

}
