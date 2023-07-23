package com.arcoiris.vendacontrole.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arcoiris.vendacontrole.models.CarrinhoModelo;

@Repository
public interface CarrinhoRepositorio extends JpaRepository<CarrinhoModelo, String> {
	Optional<CarrinhoModelo> findByCpf(String cpf);
}
