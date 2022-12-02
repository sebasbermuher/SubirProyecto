package org.iesalixar.servidor.services;

import java.util.List;

import org.iesalixar.servidor.model.Pista;

public interface PistaService {

//	public Usuario insertUsuario(Usuario usuario);
//	
//	public List<Usuario> getAllUsuarios();
//	public Usuario findUsuarioByIdModel(Long id);
//	public Usuario actualizarUsuario(Usuario usuario);
//	public Usuario eliminarUsuario(Usuario usuario);
//	
//	public Usuario getUsuarioByUserName(String username);
//	public Usuario getUsuarioByEmail(String email);
//	public Usuario getUsuarioByNif(String nif);

	public List<Pista> getAllPistas();
	public Pista findPistaByIdModel(Long id);
	public Pista actualizarPista(Pista pista);
	public Pista insertPista(Pista pista);
	public Pista eliminarPista(Pista pista);
	
	public Pista getPistaByName(String name);


}
