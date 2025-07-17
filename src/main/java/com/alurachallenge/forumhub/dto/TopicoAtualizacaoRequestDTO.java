package com.alurachallenge.forumhub.dto;

import com.alurachallenge.forumhub.entity.Topico;
import jakarta.validation.constraints.NotNull;

public record TopicoAtualizacaoRequestDTO(

        @NotNull
        Long id,
        @NotNull
        String titulo,
        @NotNull
        String mensagem,
        @NotNull
        String curso

) {
    public TopicoAtualizacaoRequestDTO(Topico topico) {
      this(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getCurso());
    }
}
