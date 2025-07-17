package com.alurachallenge.forumhub.dto;

import com.alurachallenge.forumhub.entity.Topico;
import jakarta.validation.constraints.NotNull;

public record TopicoRequestDTO(

        @NotNull
        String titulo,
        @NotNull
        String mensagem,
        @NotNull
        String curso

) {
    public TopicoRequestDTO(Topico topico){
        this(topico.getTitulo(), topico.getMensagem(), topico.getCurso());
    }

}
