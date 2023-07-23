package com.arcoiris.vendacontrole.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_itemCarrinho")
public class ItemCarrinhoModelo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private UUID produtoUuid;
    private int quantidade;
    private double subtotal;
    @ManyToOne
    @JoinColumn(name = "cpf", referencedColumnName = "cpf")
    @JsonBackReference
    private CarrinhoModelo carrinho;
    @ManyToOne
    @JoinColumn(name = "idPedido")
    @JsonBackReference
    private PedidoModelo pedidoModelo;
    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public UUID getProdutoUuid() {
        return produtoUuid;
    }

    public void setProdutoUuid(UUID produtoUuid) {
        this.produtoUuid = produtoUuid;
    }

    public void setCarrinho(CarrinhoModelo carrinho) {
        this.carrinho = carrinho;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public CarrinhoModelo getCarrinho() {
        return carrinho;
    }

    public PedidoModelo getPedidoModelo() {
        return pedidoModelo;
    }

    public void setPedidoModelo(PedidoModelo pedidoModelo) {
        this.pedidoModelo = pedidoModelo;
    }
}
