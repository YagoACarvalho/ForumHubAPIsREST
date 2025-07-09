package com.alurachallenge.forumhub.dto;

import com.alurachallenge.forumhub.entity.Topico;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoTopico(

        @NotNull
        Long id,
        @NotNull
        String titulo,
        @NotNull
        String mensagem,
        @NotNull
        String curso

) {
    public DadosAtualizacaoTopico(Topico topico) {
      this(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getCurso());
    }
}
