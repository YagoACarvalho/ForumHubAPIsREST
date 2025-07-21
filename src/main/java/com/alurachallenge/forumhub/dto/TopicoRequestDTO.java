package com.alurachallenge.forumhub.dto;

import com.alurachallenge.forumhub.entity.Topico;
import jakarta.validation.constraints.NotNull;

public record TopicoRequestDTO(

        @NotNull(message = "Titulo é obrigatório")
        String titulo,
        @NotNull(message = "Mensagem é obrigatória")
        String mensagem,
        @NotNull(message = "Curso é obrigatório")
        String curso

) {
    public TopicoRequestDTO(Topico topico){
        this(topico.getTitulo(), topico.getMensagem(), topico.getCurso());
    }

}
