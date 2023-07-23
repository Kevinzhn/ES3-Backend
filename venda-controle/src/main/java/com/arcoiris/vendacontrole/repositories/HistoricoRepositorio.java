package com.arcoiris.vendacontrole.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.arcoiris.vendacontrole.models.HistoricoModelo;
@Repository
public interface HistoricoRepositorio extends JpaRepository<HistoricoModelo, UUID> {
	Optional<HistoricoModelo> findByCpf(String cpf);
}
