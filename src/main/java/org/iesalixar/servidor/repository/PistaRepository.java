package org.iesalixar.servidor.repository;

import org.iesalixar.servidor.model.Pista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PistaRepository extends JpaRepository<Pista,Long> {

//	public Usuario findByUsername(String username);
//	public Usuario findByEmail(String email);
//	public Usuario findByNif(String nif);

	public Pista findByNombre(String nombre);
}
