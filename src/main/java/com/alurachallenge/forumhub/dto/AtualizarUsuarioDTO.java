package com.alurachallenge.forumhub.dto;

import jakarta.validation.constraints.NotNull;

public record AtualizarUsuarioDTO(

        @NotNull
        Long id,
        @NotNull
        String username,
        @NotNull
        String senha,
        @NotNull
        String curso
) {

}
