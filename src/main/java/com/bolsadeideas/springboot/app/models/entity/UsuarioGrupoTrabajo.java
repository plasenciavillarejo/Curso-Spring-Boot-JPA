package com.bolsadeideas.springboot.app.models.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Usuario_Grupo_Trabajo")
public class UsuarioGrupoTrabajo implements Serializable {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@OneToMany(mappedBy = "usuarioGrupoTrabajo", fetch = FetchType.LAZY)
	private List<Usuario> idUsuario;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@OneToMany(mappedBy = "usuarioGrupoTrabajo", fetch = FetchType.LAZY)
	private List<GrupoTrabajo> idGrupo;

	public List<Usuario> getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(List<Usuario> idUsuario) {
		this.idUsuario = idUsuario;
	}


	public List<GrupoTrabajo> getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(List<GrupoTrabajo> idGrupo) {
		this.idGrupo = idGrupo;
	}


	private static final long serialVersionUID = 1L;

}
