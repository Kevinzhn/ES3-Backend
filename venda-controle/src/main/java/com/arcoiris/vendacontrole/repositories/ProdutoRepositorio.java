package com.arcoiris.vendacontrole.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arcoiris.vendacontrole.models.ProdutoModelo;

@Repository
public interface ProdutoRepositorio extends JpaRepository<ProdutoModelo, UUID> {

}
