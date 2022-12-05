package org.iesalixar.servidor.services;

import java.util.List;

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
	}
