package com.arcoiris.vendacontrole.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record ItemCarrinhoDto(@NotNull UUID produtoUuid, @NotNull int quantidade) {
	public ItemCarrinhoDto(@NotNull UUID produtoUuid) {
		this(produtoUuid, 0);
	}
}
