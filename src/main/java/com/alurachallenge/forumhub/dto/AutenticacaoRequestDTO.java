package com.alurachallenge.forumhub.dto;

import jakarta.validation.constraints.NotNull;

public record AutenticacaoRequestDTO(
        @NotNull
        String username,
        @NotNull
        String senha) {
}
