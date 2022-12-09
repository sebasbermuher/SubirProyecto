package org.iesalixar.servidor.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.iesalixar.servidor.model.Pista;
import org.iesalixar.servidor.repository.PistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PistaServiceImpl implements PistaService {

	@Autowired
	PistaRepository pistaRepo;

	@Override
	public List<Pista> getAllPistas() {
		List<Pista> pista = pistaRepo.findAll();
		if (pista != null && pista.size() > 0) {
			return pista;
		}
		return new ArrayList<Pista>();
	}

	@Override
	public Pista findPistaByIdModel(Long id) {
		if (id != null) {
			return pistaRepo.findById(id).get();
		} else {
			return null;
		}
	}

	@Override
	public Pista actualizarPista(Pista pista) {
		if (pista == null || pista.getNombre() == null || pista.getDeporte() == null) {
			return null;
		}
		return pistaRepo.save(pista);
	}

	@Override
	public Pista insertPista(Pista pista) {
		if (pista != null && getPistaByName(pista.getNombre()) == null) {
			Pista pist = pistaRepo.save(pista);
			return pist;
		}

		return null;
	}

	@Override
	public Pista getPistaByName(String name) {
		if (name != null) {
			Pista pista = pistaRepo.findByNombre(name);
			return pista;
		}
		return null;
	}

	@Override
	public Pista eliminarPista(Pista pista) {
		if (pista != null) {
			pistaRepo.delete(pista);
		}
		return null;
	}

	@Override
	public Optional<Pista> findPistaById(Long id) {
		return pistaRepo.findById(id);
	}

}