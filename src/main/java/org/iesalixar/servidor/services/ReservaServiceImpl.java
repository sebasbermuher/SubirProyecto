package org.iesalixar.servidor.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.iesalixar.servidor.model.Pista;
import org.iesalixar.servidor.model.Reserva;
import org.iesalixar.servidor.model.Usuario;
import org.iesalixar.servidor.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservaServiceImpl implements ReservaService {

	@Autowired
	ReservaRepository reservaRepo;

	@Override
	public Reserva insertReserva(Reserva reserva) {
		if (reserva != null) {
			Reserva reser = reservaRepo.save(reserva);
			return reser;
		}
		return null;
	}

	@Override
	public List<Reserva> getAllReservas() {
		List<Reserva> reserva = reservaRepo.findAll();
		if (reserva != null && reserva.size() > 0) {
			return reserva;
		}
		return new ArrayList<Reserva>();
	}

	@Override
	public void deleteUsuarioPistaById(Reserva reserva) {
		reservaRepo.delete(reserva);

	}

	@Override
	public Reserva findReservaByIdModel(Long id) {
		if (id != null) {
			return reservaRepo.findById(id).get();
		}
		return null;
	}

	@Override
	public Reserva eliminarReserva(Reserva reserva) {
		if (reserva != null) {
			reservaRepo.delete(reserva);
		}
		return null;
	}

	@Override
	public List<Reserva> findReservaByUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		return reservaRepo.findReservasByUsuario(usuario);
	}

	@Override
	public Set<Reserva> findReservaByPista(Pista pista) {
		// TODO Auto-generated method stub
		return reservaRepo.findReservaByPista(pista);
	}

	@Override
	public Set<Reserva> ReservaUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		return reservaRepo.findReservaByUsuario(usuario);
	}

	@Override
	public Optional<Reserva> findReservaById(Long id) {
		return reservaRepo.findById(id);
	}

}