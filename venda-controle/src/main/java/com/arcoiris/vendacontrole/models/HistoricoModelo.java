package com.arcoiris.vendacontrole.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_historico")
public class HistoricoModelo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    private String cpf;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "historicoModelo")
    @JsonManagedReference
    private List<PedidoModelo> pedidos = new ArrayList<>();

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<PedidoModelo> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<PedidoModelo> pedidos) {
        this.pedidos = pedidos;
    }


}
