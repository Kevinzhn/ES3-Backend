package com.arcoiris.vendacontrole.dtos;

import jakarta.validation.constraints.NotBlank;

public record UsuarioDto(@NotBlank String cpf, @NotBlank String senha) {

}
