package com.example.algamoney.api.security.util;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.example.algamoney.api.model.Usuario;

public class UsuarioSistema extends User {

	private Usuario usuario;
	
	public UsuarioSistema(Usuario usuario, Collection<? extends GrantedAuthority> permissoes) {
		super(usuario.getEmail(), usuario.getSenha(), permissoes);
		this.usuario = usuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((!super.equals(obj)) || (getClass() != obj.getClass())){
			return false;
		}
		
		UsuarioSistema other = (UsuarioSistema) obj;
		if (usuario == null) {
			if (other.usuario != null) {
				return false;
			}
		} else if (!usuario.equals(other.usuario)) {
			return false;
		}
		return true;
	}
	
	
	
}
