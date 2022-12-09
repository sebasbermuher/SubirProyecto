package org.iesalixar.servidor.repository;

import java.util.List;
import java.util.Set;

import org.iesalixar.servidor.model.Pista;
import org.iesalixar.servidor.model.Reserva;
import org.iesalixar.servidor.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

	public List<Reserva> findReservasByUsuario(Usuario usuario);

	public Set<Reserva> findReservaByPista(Pista pista);
	
	public Set<Reserva> findReservaByUsuario(Usuario usuario);

}
