package org.iesalixar.servidor.services;

import java.util.ArrayList;
import java.util.List;

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
	public Reserva findUsuarioPistaById(Usuario id, Pista id1) {
//		if (id != null && id1 != null) {
//
//			Reserva reserva = (Reserva) reservaRepo.findByUsuarioPista(id, id1);
//			return reserva;
//		}

		return null;
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



}