package com.arcoiris.vendacontrole.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_pedido")
public class PedidoModelo implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID idPedido;
	private Date data;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pedidoModelo")
	@JsonManagedReference
	private List<ItemCarrinhoModelo> itens = new ArrayList<>();

	private Double total;
	
	@ManyToOne
	@JoinColumn(name = "cpf", referencedColumnName = "cpf")
	@JsonBackReference
	private HistoricoModelo historicoModelo;

	public Date getData() {
		return data;
	}

	public void setData(Date date) {
		this.data = date;
	}

	public List<ItemCarrinhoModelo> getItens() {
		return itens;
	}

	public void setItens(List<ItemCarrinhoModelo> itens) {
		this.itens = itens;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public UUID getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(UUID idPedido) {
		this.idPedido = idPedido;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public HistoricoModelo getHistoricoModelo() {
		return historicoModelo;
	}

	public void setHistoricoModelo(HistoricoModelo historicoModelo) {
		this.historicoModelo = historicoModelo;
	}
}
