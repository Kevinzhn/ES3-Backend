package com.arcoiris.vendacontrole.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_carrinho")
public class CarrinhoModelo implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	private String cpf;
	private double total;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "carrinho")
	@JsonManagedReference
	private List<ItemCarrinhoModelo> itens = new ArrayList<>();
	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public List<ItemCarrinhoModelo> getItens() {
		return itens;
	}

	public void setItens(List<ItemCarrinhoModelo> itens) {
		this.itens = itens;
	}

}
