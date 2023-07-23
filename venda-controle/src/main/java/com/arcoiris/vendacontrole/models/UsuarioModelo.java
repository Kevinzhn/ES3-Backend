package com.arcoiris.vendacontrole.models;

import java.io.Serial;
import java.io.Serializable;

import org.springframework.hateoas.RepresentationModel;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_usuario")
public class UsuarioModelo extends RepresentationModel<UsuarioModelo> implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@Size(min = 11, max = 11)
	private String cpf;

	private String senha;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}


}
