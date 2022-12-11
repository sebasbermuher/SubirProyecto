package org.iesalixar.servidor.repository;

import org.iesalixar.servidor.model.Pista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PistaRepository extends JpaRepository<Pista, Long> {

	public Pista findByNombre(String nombre);
}
