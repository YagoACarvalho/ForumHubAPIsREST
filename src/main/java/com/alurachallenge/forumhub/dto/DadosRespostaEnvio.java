package com.alurachallenge.forumhub.dto;

import com.alurachallenge.forumhub.entity.Usuario;
import jakarta.validation.constraints.NotNull;

public record DadosRespostaEnvio(

        @NotNull
        String mensagem
) {

}
