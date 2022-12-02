package org.iesalixar.servidor.repository;

import java.util.List;

import org.iesalixar.servidor.model.Reserva;
import org.iesalixar.servidor.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

//	public Reserva save(Reserva reserva);

	public List<Reserva> findReservasByUsuario(Usuario usuario);


}
