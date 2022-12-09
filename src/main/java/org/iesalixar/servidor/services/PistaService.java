package org.iesalixar.servidor.services;

import java.util.List;
import java.util.Optional;

import org.iesalixar.servidor.model.Pista;

public interface PistaService {

	public List<Pista> getAllPistas();

	public Pista findPistaByIdModel(Long id);

	public Pista actualizarPista(Pista pista);

	public Pista insertPista(Pista pista);

	public Pista eliminarPista(Pista pista);

	public Pista getPistaByName(String name);

	public Optional<Pista> findPistaById(Long id);

}
