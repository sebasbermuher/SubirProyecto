package org.iesalixar.servidor.services;

import java.util.List;
import java.util.Optional;

import org.iesalixar.servidor.model.Usuario;

public interface UsuarioService {

	public Usuario insertUsuario(Usuario usuario);

	public List<Usuario> getAllUsuarios();

	public Usuario findUsuarioByIdModel(Long id);

	public Usuario actualizarUsuario(Usuario usuario);

	public Usuario eliminarUsuario(Usuario usuario);

	public Usuario getUsuarioByUserName(String username);

	public Usuario getUsuarioByEmail(String email);

	public Usuario getUsuarioByNif(String nif);

	public Optional<Usuario> findUsuarioById(Long id);

}
