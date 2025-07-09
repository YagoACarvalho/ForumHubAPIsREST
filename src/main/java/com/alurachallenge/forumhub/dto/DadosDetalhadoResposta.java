package com.alurachallenge.forumhub.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record DadosDetalhadoResposta(

        Long id,
        String mensagem,
        String autor,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dataCriacao

) {
}
