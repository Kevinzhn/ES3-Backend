package com.arcoiris.vendacontrole.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arcoiris.vendacontrole.models.PedidoModelo;

@Repository
public interface PedidoRepositorio extends JpaRepository<PedidoModelo, UUID> {

}
