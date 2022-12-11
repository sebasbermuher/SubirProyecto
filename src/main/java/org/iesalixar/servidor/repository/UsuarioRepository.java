package org.iesalixar.servidor.repository;

import org.iesalixar.servidor.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	public Usuario findByUsername(String username);

	public Usuario findByEmail(String email);

	public Usuario findByNif(String nif);

}
