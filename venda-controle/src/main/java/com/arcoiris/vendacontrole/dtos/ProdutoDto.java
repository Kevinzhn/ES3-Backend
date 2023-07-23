package com.arcoiris.vendacontrole.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProdutoDto(@NotBlank String nome, @NotNull double preco, @NotBlank String descricao) {
	
}
